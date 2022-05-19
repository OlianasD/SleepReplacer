#!/bin/bash
wtds=$(ls wtds/)
for wtd in $wtds
do
	./run-docker.sh -p yes -n ppma-0
	sleep 20
	./run-warranted.sh "wtds/$wtd"
	./teardown-docker.sh ppma-0
	sleep 5
	cp test-output/emailable-report.html "test-output/emailable-report-$wtd.html"
done