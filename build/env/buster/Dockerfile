FROM debian:buster-slim

# Install basic utilities
RUN mkdir /var/run/sshd
RUN apt-get update && \
    apt-get install -y --force-yes adduser vim sudo git wget ftp curl openssh-server wget

ENV JP_HOME /home/jp
ARG user=jp
ARG group=jp
ARG uid=9998
ARG gid=9998

RUN groupadd -g ${gid} ${group} \
    && useradd -d "$JP_HOME" -u ${uid} -g ${gid} -m -s /bin/bash ${user}

RUN echo jp  ALL = NOPASSWD: ALL >> /etc/sudoers
