import mysql.connector
import re
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer


# Connect to the MySQL database
db = mysql.connector.connect(
    host="104.197.9.26",
    user="LD_Engg",
    password="B8E7B6CA",
    database="CL"
)

cursor = db.cursor()

# Prompt template
prompt_template = """
Here are some example questions you can ask:

{example_questions}

Now, ask a question related to the data in the MySQL table. Your question should follow one of these patterns:
"""

# - "How many {column_name} are there?"
# - "What are the {column_name}s?"
# - "Show me the {column_name}s for {condition}."
# - "What is the {column_name} of {condition}?"

# You can replace {column_name} with the name of a column in the table, and {condition} with a condition based on other columns (e.g., "for Industry X", "where Year is 2023", etc.).

# Function to get example questions
def get_example_questions(table_name):
    query = f"SHOW COLUMNS FROM {table_name}"
    cursor.execute(query)
    columns = cursor.fetchall()
    
    example_questions = []
    for column in columns:
        column_name = column[0]
        example_questions.append(f"How many {column_name} are there?")
        example_questions.append(f"What are the {column_name}s?")
        example_questions.append(f"Show me the {column_name}s for Industry X.")
        example_questions.append(f"What is the {column_name} of Company Y?")
    
    return "\n".join(example_questions)
# Function to normalize and parse the question
def normalize_and_parse(question, table_name):
    # Tokenize the question
    tokens = word_tokenize(question.lower())
    
    # Remove stop words
    stop_words = set(stopwords.words('english'))
    filtered_tokens = [word for word in tokens if word not in stop_words]
    
    # Stem the words
    stemmer = PorterStemmer()
    stemmed_tokens = [stemmer.stem(word) for word in filtered_tokens]
    
    # Get the column names from the table
    query = f"SHOW COLUMNS FROM {table_name}"
    cursor.execute(query)
    columns = [column[0].lower() for column in cursor.fetchall()]
    
    # Match the stemmed tokens with column names
    column_name = None
    for token in stemmed_tokens:
        if token in columns:
            column_name = columns[columns.index(token)]
            # column_name = token
            break
    
    # Parse the condition
    condition_match = re.search(r"for (\w+)|where (\w+)", question)
    condition = None
    if condition_match:
        condition = condition_match.group(1) or condition_match.group(2)
    
    return column_name, condition

# Function to answer the question
def answer_question(question, table_name):
    column_name, condition = normalize_and_parse(question, table_name)

    # # Parse the question to extract the column name and condition
    # column_match = re.search(r"the (\w+)s?", question)
    # condition_match = re.search(r"for (\w+)|where (\w+)", question)
    
    if column_name:
        if "how many" in question.lower():
            query = f"SELECT COUNT(DISTINCT `{column_name}`) FROM {table_name}"
            print(",,,,,,,,,,,,,,,,,,",query)
            if condition:
                query += f" WHERE {condition}"
            cursor.execute(query)
            result = cursor.fetchone()[0]
            return f"There are {result} {column_name}s."
    # if column_match:
    #     column_name = column_match.group(1)
        if "how many" in question.lower():
            query = f"SELECT COUNT(DISTINCT {column_name}) FROM {table_name}"
            if condition:
                condition = condition.group(1) or condition.group(2)
                query += f" WHERE {condition}"
            cursor.execute(query)
            result = cursor.fetchone()[0]
            return f"There are {result} {column_name}s."
        elif "what are" in question.lower():
            query = f"SELECT DISTINCT {column_name} FROM {table_name}"
            if condition:
                condition = condition.group(1) or condition.group(2)
                query += f" WHERE {condition}"
            cursor.execute(query)
            results = [row[0] for row in cursor.fetchall()]
            return f"The {column_name}s are: {', '.join(results)}"
        
        elif "show me" in question.lower():
            query = f"SELECT {column_name} FROM {table_name}"
            if condition:
                condition = condition.group(1) or condition.group(2)
                query += f" WHERE {condition}"
            cursor.execute(query)
            results = [row[0] for row in cursor.fetchall()]
            return f"The {column_name}s for {condition} are: {', '.join(results)}"
        
        elif "what is" in question.lower():
            query = f"SELECT {column_name} FROM {table_name}"
            if condition:
                condition = condition.group(1) or condition.group(2)
                query += f" WHERE {condition}"
            cursor.execute(query)
            result = cursor.fetchone()[0]
            return f"The {column_name} of {condition} is: {result}"
    
    return "I'm sorry, I couldn't understand your question."

# Example usage
table_name = "COMMENTSANDRESPONSES_MASTER"
example_questions = get_example_questions(table_name)
# print(example_questions)
prompt = prompt_template.format(example_questions=example_questions)
print(prompt)

while True:
    question = input("Ask a question: ")
    if question.lower() == "exit":
        break
    answer = answer_question(question, table_name)
    print(answer)

# Close the database connection
cursor.close()
db.close()