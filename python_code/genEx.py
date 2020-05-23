# IMPORT LIBRARIES

import json, csv, random, re
import matplotlib as plt
import pandas as pd
import firebase_admin
from firebase_admin import credentials, firestore
import spacy
from spacy import displacy

# FIREBASE AUTHENTICATION

cred = credentials.Certificate("E:/IMPORTANT/firebase/auth.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

# IMPORT DATASETS

def importcsv():
    # Import a csv into a dataframe
    df = pd.read_csv('E:/git_project/python_code/datasets/output.csv')
    return df

# CLEAN DATAFRAME

df_raw = importcsv()
def clean_df(df):
    # Locate and delete parenthesis like : My name is Elie (formal)
    df[~df.english.str.contains('...')]
    for row in range(0, df.shape[0]):
        df.loc[row,"english"] = re.sub(r" ?\([^)]+\)", "", df.loc[row,"english"])
    return df
df = clean_df(df_raw)
print(df.head())
# Load spacy english version
nlp = spacy.load("en_core_web_sm")

def create_json(sentence, answer):
    exercise = {
    "id": 1,
    "title": "",
    "sentence": sentence,
    "answer": answer
    }
    return exercise

def create_df():
    # Create DataFrame with column names but empty rows
    exercises = {
    'Sentence': [],
    'Answer': []
    }
    df = pd.DataFrame(exercises,columns=['Sentence', 'Answer'])
    return df

def create_1hole(sentence):
    # Split the sentence into a list of words
    words=sentence.split(" ")
    # Generate random index
    index=random.randint(0,len(words)-1)
    # Save a random word
    word=words[index]
    # Replace the word with '___'
    words[index]='/'
    # Join the list of words into a sentence (string)
    words=' '.join(words)
    return words, word



def postFill1Hole(before, after, answer, french, count, type):
    # Post the exercises FillGap with 1 hole
    doc_ref = db.collection('grammary').document(type).collection("exercises").document()
    doc_ref.set({
        u'before': before,
        u'after': after,
        u'answer':answer,
        u'french': french
    })

# Ask the user the desired type of exercises
print("\n--------------------------------------------------------------------------------")
print("\n\n-- Type of exercise| nb of answers | type of input\n")
print("1. Fill in the gap | 1 hole | keyboard")
print("2. Find words by type| Depends | click on words")
print("3. Translate Fr to En | keyboard")
print("4. Fill in the gap | 1 hole | 3 distractors | tap")

exercise = int(input("What type of exercise do you want to create ? : "))
qty = int(input("How many ? : "))

def random_sentence(df):
    # Create random index to choose a sentence
    index = random.randint(0,df.shape[0]-1)
    # Tokenize random sentence of the dataframe
    sentence = nlp(df.loc[index,"english"])
    return sentence

if exercise == 1:
    type = 'Fillgap_1_0_kb'
    exercise_df = pd.DataFrame({})
    for i in range(1,qty+1):
        theta = round(random.uniform(-3, 3), 2)
        print('generating ... (',i,'/',qty,')')
        index = random.randint(0,df.shape[0]-1)
        # Tokenize random sentence of the dataframe
        raw = nlp(df.loc[index,"english"])
        french = df.loc[index,"french"]
        i=0
        distractors=[]
        print(index)
        indexes_list=[]
        words=[]
        for token in raw:
            if token.pos_ == 'VERB' or token.pos_ == 'ADP' or token.pos_ == 'NOUN':
                indexes_list.append(i)
                words.append(token.text)
            i+=1
        words
        word = random.choice(words)
        index_word = str(raw).find(word)

        #word = str(raw[indexword])
        #words=str(raw).split(" ")
        before = str(raw)[:index_word]
        after = str(raw)[index_word+len(word):]
        exercise_df = exercise_df.append({'before': before, 'after': after, 'answer': word, 'french': french, 'theta':theta}, ignore_index=True)
    print('generated ',qty, ' exercises.')
    print(exercise_df)
    posting_yn = input("Do you want to upload it to the database ? (y/n) : ")
    if posting_yn == "y":
        for i in range(0,exercise_df.shape[0]):
            doc_ref = db.collection('grammary').document(type).collection("exercises").document()
            doc_ref.set({
                u'before': exercise_df.iloc[i]['before'],
                u'after': exercise_df.iloc[i]['after'],
                u'answer':exercise_df.iloc[i]['answer'],
                u'french': exercise_df.iloc[i]['french'],
                u'theta': theta
            })
            print('uploading ... (',i+1,'/',qty,')')
        print('uploaded ', qty, ' exercises.')
    elif posting_yn == "n":
        print("Okay, bye.")
elif exercise == 2:
    ## Find verbs in the sentence and list them
    sentence_array=[]
    answers=[]
    exercise_df = create_df()
    for i in range(1,qty+1):
        theta = round(random.uniform(-3, 3), 2)
        sentence_array = []
        answers = []
        print('generating ... (',i,'/',qty,')')
        raw = nlp(str(random_sentence(df)))
        raw2=str(raw).split(" ")
        indexes_answers=0
        for token in raw:
            sentence_array.append(token.text)
            if token.pos_ == 'VERB':
                answers.append(indexes_answers)
            indexes_answers+=1
        exercise_df = exercise_df.append({'Sentence': sentence_array, 'Answer': answers, 'theta':theta}, ignore_index=True)
    print('generated ',qty, ' exercises.')
    print(exercise_df)
    type = "FindVerbs_random_0_tap"
    posting_yn = input("Do you want to upload it to the database ? (y/n) : ")
    if posting_yn == "y":
        for i in range(0,exercise_df.shape[0]):
            doc_ref = db.collection('grammary').document(type).collection("exercises").document()
            doc_ref.set({
                u'sentence': exercise_df.iloc[i]['Sentence'],
                u'answer': exercise_df.iloc[i]['Answer'],
                u'theta':theta
            })
            print('uploading ... (',i+1,'/',qty,')')
        print('uploaded ', qty, ' exercises.')
    elif posting_yn == "n":
        print("Okay, bye.")
elif exercise == 3:
    type = 'TransFrToEn_0_0_kb'
    exercise_df = create_df()
    punct = '!"#$%&\()*+./:;<=>?@[\\]^_`{}~'   # `|` is not present here
    transtab = str.maketrans(dict.fromkeys(punct, ''))
    df['french'] = '|'.join(df['french'].tolist()).translate(transtab).split('|')
    for i in range(1,qty+1):
        theta = round(random.uniform(-3, 3), 2)
        print('generating ... (',i,'/',qty,')')
        index = random.randint(0,df.shape[0])
        english = df.loc[index,"english"]
        french = df.loc[index,"french"]
        exercise_df = exercise_df.append({'Sentence': french, 'Answer': english, 'theta':theta}, ignore_index=True)
    print('generated ',qty, ' exercises.')
    print(exercise_df)
    posting_yn = input("Do you want to upload it to the database ? (y/n) : ")
    if posting_yn == "y":

        for i in range(0,exercise_df.shape[0]):
            doc_ref = db.collection('grammary').document(type).collection("exercises").document()
            doc_ref.set({
                u'french_sentence': exercise_df.iloc[i]['Sentence'],
                u'english_sentence': exercise_df.iloc[i]['Answer'],
                u'theta':theta
            })
            print('uploading ... (',i+1,'/',qty,')')
        print('uploaded ', qty, ' exercises.')
    elif posting_yn == "n":
        print("Okay, bye.")
elif exercise == 4:
    type = 'Fillgap_1_3_tap'
    print('Loading ... (it can take a while)')
    nlp = spacy.load("en_core_web_md")
    print('spaCy loaded !')
    exercise_df = pd.DataFrame({})
    def most_similar(word):
        queries = [w for w in word.vocab if w.is_lower == word.is_lower and w.prob >= -15]
        by_similarity = sorted(queries, key=lambda w: word.similarity(w), reverse=True)
        return by_similarity[1:4]
    for i in range(1,qty+1):
        theta = round(random.uniform(-3, 3), 2)
        print('generating ... (',i,'/',qty,')')
        raw = nlp(str(random_sentence(df)))
        index = random.randint(0,df.shape[0]-1)
        # Tokenize random sentence of the dataframe
        raw = nlp(df.loc[index,"english"])
        french = df.loc[index,"french"]
        i=0
        distractors=[]
        indexes_list=[]
        words=[]
        for token in raw:
            if token.pos_ == 'VERB' or token.pos_ == 'ADP' or token.pos_ == 'NOUN':
                indexes_list.append(i)
                words.append(token.text)
            i+=1
        words
        word = random.choice(words)
        index_word = str(raw).find(word)

        #word = str(raw[indexword])
        #words=str(raw).split(" ")
        before = str(raw)[:index_word]
        after = str(raw)[index_word+len(word):]
        distractors = [w.lower_ for w in most_similar(nlp.vocab[word])]
        distractors
        exercise_df = exercise_df.append({'before': before, 'after':after, 'answer': word, 'distractors': distractors, 'french': french, 'theta': theta}, ignore_index=True)
    print('generated ',qty, ' exercises.')
    print(exercise_df)
    posting_yn = input("Do you want to upload it to the database ? (y/n) : ")
    if posting_yn == "y":
        for i in range(0,exercise_df.shape[0]):
            doc_ref = db.collection('grammary').document(type).collection("exercises").document()
            doc_ref.set({
                u'french_sentence': exercise_df.iloc[i]['french'],
                u'before': exercise_df.iloc[i]['before'],
                u'after': exercise_df.iloc[i]['after'],
                u'answer': exercise_df.iloc[i]['answer'],
                u'distractors': exercise_df.iloc[i]['distractors'],
                u'theta':theta
            })
            print('uploading ... (',i+1,'/',qty,')')
        print('uploaded ', qty, ' exercises.')
    elif posting_yn == "n":
        print("Okay, bye.")

# TO DO: Translate with 15% distractors and separated words (bubbles like Duolingo)

"""
elif exercise == 5:
    type = 'TransFrToEn_0_15%_tap'
    exercise_df = create_df()
    #punct = '!"#$%&\()*+./:;<=>?@[\\]^_`{}~'   # `|` is not present here
    transtab = str.maketrans(dict.fromkeys(punct, ''))
    df['french'] = '|'.join(df['french'].tolist()).translate(transtab).split('|')
    for i in range(1,qty+1):
        print('generating ... (',i,'/',qty,')')
        index = random.randint(0,df.shape[0])
        english = df.loc[index,"english"]
        french = df.loc[index,"french"]
        exercise_df = exercise_df.append({'Sentence': french, 'Answer': english, 'theta':theta}, ignore_index=True)
    print('generated ',qty, ' exercises.')
    print(exercise_df)
    posting_yn = input("Do you want to upload it to the database ? (y/n) : ")
    if posting_yn == "y":

        for i in range(0,exercise_df.shape[0]):
            doc_ref = db.collection('grammary').document(type).collection("exercises").document()
            doc_ref.set({
                u'french_sentence': exercise_df.iloc[i]['Sentence'],
                u'english_sentence': exercise_df.iloc[i]['Answer'],
                u'theta':theta
            })
            print('uploading ... (',i+1,'/',qty,')')
        print('uploaded ', qty, ' exercises.')
    elif posting_yn == "n":
        print("Okay, bye.")
"""
