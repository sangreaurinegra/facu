# Docker file Master que extiene el master prepara copias locales y ajustes para la ejecucion en cluster
FROM sangreaurinegra/master:0.1.1

MAINTAINER SangreAurinegra <sangreaurinegra@gmail.com>

#RUN rm -r /resources

RUN rm /root/run-wordcount.sh

ADD workingdir/ /root/workingdir/

ENV WORKING_DIR /root/workingdir/



# intentos de seteo y manejo de multiples carpetas desde hadoop
#ADD conf/* /tmp/
#RUN rm $HADOOP_INSTALL/etc/hadoop/hdfs-site.xml && \ 
#mv /tmp/hdfs-site.xml $HADOOP_INSTALL/etc/hadoop/hdfs-site.xml && \ 
#mv /tmp/hadoop-env.sh /usr/local/hadoop/etc/hadoop/hadoop-env.sh
#mv /tmp/hadoop-env.sh /root/workingdir/hadoop-env.sh


#ADD resources/ /root/resources/
#ADD input/ /root/input/
#ADD output/ /root/output/