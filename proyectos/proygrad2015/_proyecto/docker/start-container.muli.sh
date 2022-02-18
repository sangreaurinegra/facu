#!/bin/bash

# run N slave containers
N=$1

# the defaut node number is 3
if [ $# = 0 ]
then
	N=3
fi


# delete old master container and start new master container
docker rm -f master &> /dev/null

echo "start master container..."
#docker exec master rm -rf /root/workingdir/hdfs && mkdir -p /root/workingdir/hdfs &> /dev/null

# Multi Core
docker run --cpuset-cpus="0" -v /datos/disco1:/root/workingdir/disco1 -v /datos/hdfs/master:/root/hdfs  -d -t --dns 127.0.0.1 -P --name master -h master.kiwenlau.com -w /root master:0.1.0 &> /dev/null


# get the IP address of master container
FIRST_IP=$(docker inspect --format="{{.NetworkSettings.IPAddress}}" master)

# delete old slave containers and start new slave containers
i=1
while [ $i -lt $N ]
do
	docker rm -f slave$i &> /dev/null

	echo "start slave$i container..."

	docker run --cpuset-cpus="$i" -v /datos/hdfs/slave$i:/root/hdfs -d -t --dns 127.0.0.1 -P --name slave$i -h slave$i.kiwenlau.com -e JOIN_IP=$FIRST_IP slave:0.1.0 &> /dev/null

	((i++))
done

docker exec -it master bash
