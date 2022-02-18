# Docker file Master que extiene el master prepara copias locales y ajustes para la ejecucion en cluster
FROM sangreaurinegra/slave:0.1.1

MAINTAINER SangreAurinegra <sangreaurinegra@gmail.com>

#RUN rm -r /resources

ADD workingdir/ /root/workingdir/

RUN rm /root/.bashrc
ADD resources/.bashrc /root/.bashrc

ENV WORKING_DIR /root/workingdir/

WORKDIR /root/workingdir/

RUN eval `/root/.ureka/ur_setup`


# intentos de seteo y manejo de multiples carpetas desde hadoop
#ADD conf/* /tmp/

#RUN rm $HADOOP_INSTALL/etc/hadoop/hdfs-site.xml && \ 
#mv /tmp/hdfs-site.xml $HADOOP_INSTALL/etc/hadoop/hdfs-site.xml && \ 
#mv /tmp/hadoop-env.sh /usr/local/hadoop/etc/hadoop/hadoop-env.sh

