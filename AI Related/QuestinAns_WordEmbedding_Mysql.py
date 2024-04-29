import mysql.connector
from gensim.models import Word2Vec
from gensim.test.utils import common_texts
from nltk.tokenize import word_tokenize
import numpy as np
import nltk

# sentences = [["cat", "say", "meow"], ["dog", "say", "woof"]]
# model = Word2Vec(sentences, min_count=1)

# Load pre-trained Word2Vec model
w2v_model = Word2Vec.load('C:\\Users\\adhim\\OneDrive\\Desktop\\Skills-2024\\word2vec_SEC_COMMENT.model')
# w2v_model = Word2Vec(results, min_count=1,
#                                 vector_size=100, window=5)
# w2v_model = Word2Vec(sentences=common_texts, vector_size=100, window=5, min_count=1, workers=4)
# w2v_model.save("word2vec.model")

# w2v_model = Word2Vec.load("word2vec.model")
# w2v_model.wv.most_similar(positive=['woman', 'king'], negative=['man'], topn=1)
# w2v_model.wv.doesnt_match("breakfast cereal dinner lunch";.split())
# w2v_model.wv.similarity('woman', 'man')
# sims = w2v_model.wv.most_similar('compensation', topn=10)  # get other similar words
# print("Similar Words:::",sims)


# Example query
query = "volatility"

# Tokenize and average word embeddings for the query
query_tokens = word_tokenize(query.lower())
query_vector = np.mean([w2v_model.wv[token] for token in query_tokens if token in w2v_model.wv], axis=0)
print("Input tokens-----------",query_tokens)

# Connect to MySQL database
db_connection = mysql.connector.connect(
    host="104.197.9.26",
    user="LD_Engg",
    password="B8E7B6CA",
    database="CL"
)
cursor = db_connection.cursor()

# Retrieve context from MySQL database and calculate similarity
cursor.execute("SELECT SEC_COMMENT FROM COMMENTSANDRESPONSES_MASTER WHERE CR_INFO_REF_ID = 10231428")
results = cursor.fetchall()

similarity_scores = []
for row in results:
    text = row[0]  # Assuming the text is in the first column
    doc_tokens = word_tokenize(text.lower())
    print("DB data tokens::::",doc_tokens)
    doc_vector = np.mean([w2v_model.wv[token] for token in doc_tokens if token in w2v_model.wv], axis=0)
    print("doc_vector::::",doc_vector)
    similarity = np.dot(query_vector, doc_vector) / (np.linalg.norm(query_vector) * np.linalg.norm(doc_vector))
    print("similarity--------",similarity)
    similarity_scores.append((text, similarity))

# Sort by similarity score
similarity_scores.sort(key=lambda x: x[1], reverse=True)

# Print top results
for i, (text, score) in enumerate(similarity_scores[:5]):
    print(f"Result {i+1}: {text} (Similarity Score: {score})")

# Close database connection
cursor.close()
db_connection.close()