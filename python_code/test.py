import json
import csv
import random
import pandas as pd
import spacy
from spacy import displacy
import re
import lemminflect
def importcsv():
    # Import a csv into a dataframe
    df = pd.read_csv('E:/Project_learning_app/datasets/matthieu.csv')
    return df

################################################################################################

df_raw = importcsv()
def clean_df(df):
    # Locate and delete parenthesis like : My name is Elie (formal)
    for row in range(0, df.shape[0]):
        df.loc[row,"English"] = re.sub(r" ?\([^)]+\)", "", df.loc[row,"English"])
    return df
df = clean_df(df_raw)
print(df)
spacy.explain('RB')
doc = nlp("I didn't like it")
for token in doc:
    print(token.text, token.lemma_, token.pos_, token.tag_, token.dep_,
            token.shape_, token.is_alpha, token.is_stop)

index = random.randint(0,df.shape[0])
nlp = spacy.load("en_core_web_sm")


english = nlp(df.loc[index,"English"])
french = nlp(df.loc[index,"French"])
spacy.explain('VB')
print(english, french)

def most_similar(word):
    queries = [w for w in word.vocab if w.is_lower == word.is_lower and w.prob >= -10]
    by_similarity = sorted(queries, key=lambda w: word.similarity(w), reverse=True)
    return by_similarity[:10]
print([w.lower_ for w in most_similar(nlp.vocab[u'i'])])
"""
answers=[]
for token in raw:
    print(token.text, token.lemma_, token.pos_, token.tag_, token.dep_,
            token.shape_)
    if token.pos_ == 'VERB':
        answers.append(token)
print(answers)
"""
"""

sentence="My name is Elie and I don't like dogs, I prefer other things."



tokens = nltk.word_tokenize(sentence)
tokens
tagged = nltk.pos_tag(tokens)
tagged
synonyms = []
for syn in wordnet.synsets("car"):
    for l in syn.lemmas():
        synonyms.append(l.name())


brown_news_tagged = brown.tagged_words(categories='news', tagset='universal')
tag_fd = nltk.FreqDist(tag for (word, tag) in brown_news_tagged)
tag_fd.most_common()
#tag_fd.plot(cumulative=False)

wsj = nltk.corpus.treebank.tagged_words(tagset='universal')
word_tag_fd = nltk.FreqDist(wsj)
[wt[0] for (wt, _) in word_tag_fd.most_common() if wt[1] == 'VERB']




def analyze_words(words):
    for w in words:
        tmp = wn.synsets(w)[0].pos()
        print(w, ":", tmp)
"""
