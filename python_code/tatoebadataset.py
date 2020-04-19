import pandas as pd
import re

df=pd.read_csv('datasets/eng_sentences.tsv',delimiter='\t',encoding='utf-8')
df.columns = ['id', 'lang', 'english']
df = df.drop(['id', 'lang'], 1)
print(df.head())

#punct = '!"#$%&\()*+./:;<=>?@[\\]^_`{}~'

transtab = str.maketrans(dict.fromkeys(punct, ''))

df['english'] = '|'.join(df['english'].tolist()).translate(transtab).split('|')
df=pd.read_csv('datasets/shorted_dataset.csv',encoding='utf-8')
print(df.head())
df2 = df[:1200]

from translate import Translator
import csv
#from googletrans import Translator
translations=[]
for row in range(167, df2.shape[0]):
    translator = Translator(to_lang='fr', de='brosseteli@eisti.eu')
    translation = translator.translate(df2.loc[row,"english"])
    translations.append(translation)
    print(translation)

#translations = translator.translate(df2['english'], dest='fr')
#df2['english'].apply(translator.translate)
french_df = pd.DataFrame({'french': translations})
count=0
for row in range(0, df2.shape[0]):
    count += df2.loc[row,"english"].count(' ') + 1

print(count)
df['french'] = '|'.join(df['french'].tolist()).translate(transtab).split('|')
df.to_csv('datasets/cleaned_dataset.csv', encoding='utf-8', index=False)
print('Completed !')


#df = df.fillna('')
#df.loc[df['french'].str.contains(None), 'french'] = ''
print(df['french'].count())
print(df.tail())


df = df[df['english'].str.split().str.len().lt(8)]
print(df.shape[0])
#df.to_csv('datasets/shorted_dataset.csv', encoding='utf-8', index=False)

df=pd.read_csv('datasets/output.tsv',delimiter='\t',encoding='utf-8')
df.loc[df['french'].str.contains("MYMEMORY"), 'french'] = None
df = df.dropna()
df
df.to_csv('datasets/1_306.csv', encoding='utf-8', index=False)
punct = '!"#$%&\()*+./:;<=>@[\\]_`{}~'   # `|` is not present here
transtab = str.maketrans(dict.fromkeys(punct, ''))
df['french'] = '|'.join(df['french'].tolist()).translate(transtab).split('|')
print('dropping punctuation ...')
#df.to_csv('datasets/onlyenglishwithoutpunctuation.csv', encoding='utf-8', index=False)
df.shape[0]
df = df[df['french'].str.split().str.len().lt(11)]
df.shape[0]
df
df.to_csv('datasets/1_306_wpunctuation.csv', encoding='utf-8', index=False)
