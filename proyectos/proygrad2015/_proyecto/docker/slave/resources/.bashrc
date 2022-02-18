export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64 
export HADOOP_INSTALL=/usr/local/hadoop 
export HADOOP_HOME=$HADOOP_INSTALL 
export PATH=$PATH:$HADOOP_INSTALL/bin  
export PATH=$PATH:$HADOOP_INSTALL/sbin  
export HADOOP_MAPRED_HOME=$HADOOP_INSTALL  
export HADOOP_COMMON_HOME=$HADOOP_INSTALL  
export HADOOP_HDFS_HOME=$HADOOP_INSTALL
export HADOOP_CONF_DIR=$HADOOP_INSTALL/etc/hadoop   
export YARN_HOME=$HADOOP_INSTALL
export YARN_CONF_DIR=$HADOOP_INSTALL/etc/hadoop
ur_setup() {
    eval `/root/.ureka/ur_setup -sh $*`
}
ur_forget() {
    eval `/root/.ureka/ur_forget -sh $*`
}

ur_setup