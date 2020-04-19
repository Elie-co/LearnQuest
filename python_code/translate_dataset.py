import pandas as pd

df=pd.read_csv('datasets/eng_sentences.tsv',delimiter='\t',encoding='utf-8')
def clean(df):
    df=pd.read_csv('datasets/eng_sentences.tsv',delimiter='\t',encoding='utf-8')
    df.columns = ['id', 'lang', 'english']
    df = df.drop(['id', 'lang'], 1)

    punct = '!"#$%&\()*+./:;<=>?@[\\]^_`{}~'   # `|` is not present here
    transtab = str.maketrans(dict.fromkeys(punct, ''))
    df['english'] = '|'.join(df['english'].tolist()).translate(transtab).split('|')
    print('dropping punctuation ...')
    #df.to_csv('datasets/onlyenglishwithoutpunctuation.csv', encoding='utf-8', index=False)
    df.shape[0]
    df = df[df['english'].str.split().str.len().lt(8)]
    df.shape[0]
    print('dataframe loaded')
    with open('datasets/onlyenglishwithoutpunctuation.tsv','w', encoding='utf-8') as write_tsv:
        write_tsv.write(df.to_csv(sep='\t', index=False))
    print('output created.\n')
def count_words(df):
    count=0
    for row in range(0, df.shape[0]):
        count += df.loc[row,"english"].count(' ') + 1
    return count


#df = clean(df)

df=pd.read_csv('datasets/onlyenglishwithoutpunctuation.tsv',delimiter='\t',encoding='utf-8')
df2 = df[:305]
print('number of words contained in the dataset: ', count_words(df2))

from translate import Translator
translator = Translator(to_lang='fr', de='brosseteli@eisti.eu')
"""
translations=[]
for row in range(167, df2.shape[0]):
    translator = Translator(to_lang='fr', de='brosseteli@eisti.eu')
    translation = translator.translate(df2.loc[row,"english"])
    translations.append(translation)
    print(translation)
"""
df2['french'] = df2['english'].apply(translator.translate)
print(df2.head())
df2.to_csv('datasets/output.csv', encoding='utf-8', index=False)
with open('datasets/output.tsv','w', encoding='utf-8') as write_tsv:
    write_tsv.write(df2.to_csv(sep='\t', index=False))
