"""
df=pd.read_csv('datasets/eng_sentences.tsv',delimiter='\t',encoding='utf-8')
def clean(df, col):
    df=pd.read_csv('datasets/eng_sentences.tsv',delimiter='\t',encoding='utf-8')
    df.columns = ['id', 'lang', col]
    df = df.drop(['id', 'lang'], 1)

    #punct = '!"#$%&\()*+./:;<=>?@[\\]^_`{}~'   # `|` is not present here
    transtab = str.maketrans(dict.fromkeys(punct, ''))
    df[col] = '|'.join(df[col].tolist()).translate(transtab).split('|')
    print('dropping punctuation ...')
    #df.to_csv('datasets/onlyenglishwithoutpunctuation.csv', encoding='utf-8', index=False)
    df.shape[0]
    df = df[df[col].str.split().str.len().lt(8)]
    df.shape[0]
    print('dataframe loaded')
    df.to_csv('datasets/onlyenglishwithoutpunctuation2.csv', encoding='utf-8', index=False)
    print('output created.\n')
def count_words(df):
    count=0
    for row in range(302, df.shape[0]):
        count += df.loc[row,"english"].count(' ') + 1
    return count


df = clean(df, "english")

df
"""

import pandas as pd

df=pd.read_csv('datasets/onlyenglishwithoutpunctuation2.csv',encoding='utf-8')
df
df2 = df[302:330]
#print('number of words contained in the dataset: ', count_words(df2))

from googletrans import Translator
translations=[]
for row in range(302, df2.shape[0]):
    translator = Translator()
    translation = translator.translate(df2.loc[row,"english"], dest="fr")
    translations.append(translation.text)
    print(row, " : ", translation.text)


"""
df2['french'] = df2['english'].apply(translator.translate)
df2
df3 = pd.read_csv('datasets/1_306_wpunctuation.csv',encoding='utf-8')
df3
df_row_reindex = pd.concat([df3, df2], ignore_index=True)

df_row_reindex.to_csv('datasets/output2.csv', encoding='utf-8', index=False)

df3 = pd.read_csv('datasets/output2.csv',encoding='utf-8')

#with open('datasets/output.tsv','w', encoding='utf-8') as write_tsv:
#    write_tsv.write(df2.to_csv(sep='\t', index=False))
"""
