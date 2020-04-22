import pandas as pd
from translate import Translator

df=pd.read_csv('datasets/onlyenglishwithoutpunctuation2.csv',encoding='utf-8')
df

before = 1401
after = 1600
df2 = df[before:after]
#print('number of words contained in the dataset: ', count_words(df2))

translations=[]
translator= Translator(to_lang="fr")
for row in range(before, after):

    translation = translator.translate(df2.loc[row,"english"])
    translations.append(translation)
    print(row, " : ", df2.loc[row,"english"], translation)
df2['french'] = translations
df2.to_csv('datasets/output7.csv', encoding='utf-8', index=False)


d=pd.read_csv('datasets/output2.csv',encoding='utf-8')
df1=pd.read_csv('datasets/output3.csv',encoding='utf-8')
df2=pd.read_csv('datasets/output4.csv',encoding='utf-8')
df3=pd.read_csv('datasets/output5.csv',encoding='utf-8')
df4=pd.read_csv('datasets/output6.csv',encoding='utf-8')
df5=pd.read_csv('datasets/output7.csv',encoding='utf-8')

df = pd.concat([d, df], ignore_index=True)
df
df.to_csv('datasets/output.csv', encoding='utf-8', index=False)
