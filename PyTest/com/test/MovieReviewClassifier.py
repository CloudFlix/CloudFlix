'''
Created on Oct 10, 2013

@author: pratiksomanagoudar
'''
import nltk.classify.util
from nltk.classify import NaiveBayesClassifier
from nltk.corpus import movie_reviews

negids = movie_reviews.fileids('neg')
posids = movie_reviews.fileids('pos')

all_neg_words = nltk.FreqDist(w.lower() for w in movie_reviews.words(negids))
all_pos_words = nltk.FreqDist(w.lower() for w in movie_reviews.words(posids)) 
print all_neg_words.keys()[2000:]                            
