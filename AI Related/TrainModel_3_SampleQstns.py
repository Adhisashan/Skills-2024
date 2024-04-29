import mysql.connector
import gensim
from gensim.models import Word2Vec
import nltk
from collections import Counter

# Connect to the MySQL database
db = mysql.connector.connect(
    host="104.197.9.26",
    user="LD_Engg",
    password="B8E7B6CA",
    database="CL"
)

cursor = db.cursor()
# Fetch data from the database
query = "SELECT DISTINCT mas.`COMPANY_NAME`, sb.`SASB_INDUSTRY` AS INDUSTRY , s.`NEW_NORM_TOPIC` AS TOPIC, s.`SEC_COMMENT`, s.`ISSUER_RESPONSE` \
FROM CL.`COMMENTSANDRESPONSES_MASTER` s \
INNER JOIN live_comp.`COMP_MASTER` mas ON mas.`CIK_CODE` = s.`CIK_CODE`\
LEFT JOIN live_comp.`COMP_INFO` comp ON comp.`COMPANY_ID` = mas.`CIK_CODE`\
LEFT JOIN live_comp.`M_SASB_SECTOR` sb ON sb.`SASB_SECTOR_ID` = comp.`SASB_SECTOR_ID`\
WHERE mas.`COMPANY_FILTER` REGEXP 'DOW30' AND s.`UPLOAD_SEC_IDX_DATE` between '2024-01-01' and '2025-01-01';"
cursor.execute(query)
data = cursor.fetchall()

# Preprocess the data
tokenized_data = []
sentences = []
for company_name, industry, topic, question, response in data:
    company_name_tokens = [str(word.lower()) for word in nltk.word_tokenize(str(company_name))]
    industry_tokens = [str(word.lower()) for word in nltk.word_tokenize(str(industry))]
    topic_tokens = [str(word.lower()) for word in nltk.word_tokenize(str(topic))]
    question_tokens = [str(word.lower()) for word in nltk.word_tokenize(str(question))]
    response_tokens = [str(word.lower()) for word in nltk.word_tokenize(str(response))]
    tokenized_data.append((company_name_tokens, industry_tokens, topic_tokens, question_tokens, response_tokens))
    sentences.append(company_name_tokens + industry_tokens + topic_tokens + question_tokens + response_tokens)

# Close the cursor
cursor.close()


# Initialize the Word2Vec model
model = Word2Vec(sentences=sentences, vector_size=100, window=5, min_count=1, workers=4)

# Build the vocabulary
model.build_vocab(sentences)

# Train the model
model.train(sentences, total_examples=model.corpus_count, epochs=10)

# Save the trained model
model.save("word2vec_CL.model")

# Function to answer questions
def answer_question(question):
    db = mysql.connector.connect(
        host="104.197.9.26",
        user="LD_Engg",
        password="B8E7B6CA",
        database="CL"
    )
    cursor = db.cursor()
    
    tokenized_question = [word.lower() for word in nltk.word_tokenize(question)]
    
    # "How many companies in 2024"
    if "how many companies" in question.lower() and "2024" in question.lower():
        query = "SELECT DISTINCT COUNT(DISTINCT mas.`CIK_CODE`) FROM CL.`COMMENTSANDRESPONSES_MASTER` s \
INNER JOIN live_comp.`COMP_MASTER` mas ON mas.`CIK_CODE` = s.`CIK_CODE` \
WHERE mas.`COMPANY_FILTER` REGEXP 'DOW30' AND s.`UPLOAD_SEC_IDX_DATE` > '2024-01-01'"
        cursor.execute(query)
        result = cursor.fetchone()[0]
        return f"There are {result} companies in 2024."

# "Show the list of Questions and Answers for specific Industry"
    if "show the list of questions and answers for" in question.lower():
        industry = question.lower().split("for")[-1].strip()
        query = "SELECT DISTINCT s.`SEC_COMMENT`, s.`ISSUER_RESPONSE` FROM CL.`COMMENTSANDRESPONSES_MASTER` s \
INNER JOIN live_comp.`COMP_MASTER` mas ON mas.`CIK_CODE` = s.`CIK_CODE`\
LEFT JOIN live_comp.`COMP_INFO` comp ON comp.`COMPANY_ID` = mas.`CIK_CODE`\
LEFT JOIN live_comp.`M_SASB_SECTOR` sb ON sb.`SASB_SECTOR_ID` = comp.`SASB_SECTOR_ID`\
WHERE mas.`COMPANY_FILTER` REGEXP 'DOW30' AND SASB_INDUSTRY = %s AND s.`UPLOAD_SEC_IDX_DATE` between '2024-01-01' and '2025-01-01';"
        cursor.execute(query, (industry,))
        qa_pairs = cursor.fetchall()
        output = f"Questions and Answers for '{industry}':\n"
        for question, answer in qa_pairs:
            output += f"Q: {question}\nA: {answer}\n\n"
        return output
    
    # "What are the top 10 topics in 2023"
    if "what are the top" in question.lower() and "topics" in question.lower() and "2023" in question.lower():
        query = "SELECT DISTINCT s.`NEW_NORM_TOPIC` AS TOPIC FROM CL.`COMMENTSANDRESPONSES_MASTER` s \
WHERE s.`UPLOAD_SEC_IDX_DATE` BETWEEN '2023-01-01' AND '2024-01-01'"
        cursor.execute(query)
        topics = [row[0] for row in cursor.fetchall()]
        topic_counts = Counter(topics)
        top_topics = topic_counts.most_common(10)
        output = "Top 10 topics in 2023:\n"
        for i, (topic, count) in enumerate(top_topics, start=1):
            output += f"{i}. {topic} (Count: {count})\n"
        return output
    
    # Find the most similar response from the training data
    most_similar_response = None
    max_similarity = -1.0
    for qa in tokenized_data:
        company_name, industry, topic, question, response = qa
        similarity = model.wv.n_similarity(tokenized_question, question)
        if similarity > max_similarity:
            max_similarity = similarity
            most_similar_response = response
    
    if most_similar_response:
        return " ".join(most_similar_response)
    else:
        return "No relevant response found in the training data."

# Close the database connection
cursor.close()
db.close()

# Example usage
print(answer_question("How many companies in 2024"))
print("----------------------------")
print(answer_question("Show the list of Questions and Answers for Consumer Finance"))
print("----------------------------")
print(answer_question("What are the top 10 topics in 2023"))
print("----------------------------")
print(answer_question("What is the capital of France?"))