#!/bin/bash

num_execution=$1

for (( i = 0; i <= $num_execution; i++ ))
do
	echo "Run $i of $num_execution"
	./run-docker.sh -p yes -n ppma-0
	sleep 5
	./run-testsuite.sh
	./teardown-docker.sh ppma-0
	sleep 2
	cp test-output/emailable-report.html test-output/emailable-report-$i.html
	killall chromedriver
	killall chrome
	sleep 1
done