#!/bin/bash

echo "Seteando variables hadoop "


export HADOOP_OPTS="$HADOOP_OPTS -Ddfs.namenode.name.dir=file:///root/workingdir/hdfs/${HOSTNAME}/namenode -Ddfs.datanode.data.dir=file:///root/workingdir/hdfs/${HOSTNAME}/datanode"

rm -rf ~/hdfs/namenode

rm -rf /root/workingdir/hdfs/${HOSTNAME}/namenode

/usr/local/hadoop/bin/hdfs namenode -format

echo "Seteado " $HADOOP_OPTS

