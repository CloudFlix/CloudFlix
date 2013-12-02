
export MAHOUT_HOME=/usr/local/mahout
export MAHOUT_LOCAL=/usr/local/mahout
echo $MAHOUT_HOME

echo $MAHOUT_LOCAL

cd /usr
cd local
cd mahout

chmod 777 bin

cd bin

chmod u+x mahout

cd ..

rm -Rf temp
bin/mahout recommenditembased --input ratingsFormahout.dat --usersFile final_mp_output.dat --numRecommendations 30 --output cloud_output/ --similarityClassname SIMILARITY_PEARSON_CORRELATION