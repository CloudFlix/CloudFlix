'''
Created on Oct 8, 2013

@author: pratiksomanagoudar
'''
import nltk.classify.util
from nltk.classify import NaiveBayesClassifier
from nltk.corpus import movie_reviews
 
def word_feats(words):
    return dict([(word, True) for word in words])
 
negids = movie_reviews.fileids('neg')
posids = movie_reviews.fileids('pos')
 
negfeats = [(word_feats(movie_reviews.words(fileids=[f])), 'neg') for f in negids]
posfeats = [(word_feats(movie_reviews.words(fileids=[f])), 'pos') for f in posids]
 
negcutoff = len(negfeats)
poscutoff = len(posfeats)
 
trainfeats = negfeats[:negcutoff] + posfeats[:poscutoff]
classifier = NaiveBayesClassifier.train(trainfeats)
print classifier.classify(word_feats('too bad but good'))
classifier.prob_classify(posfeats)
 # f = open('filename')
 #lines = f.readlines()
 #f.close()
