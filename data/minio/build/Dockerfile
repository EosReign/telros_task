FROM minio/minio:RELEASE.2024-10-02T17-50-41Z

EXPOSE 5800 9000 9001

RUN curl https://dl.min.io/client/mc/release/linux-amd64/mc --create-dirs -o ${HOME}/minio-binaries/mc && \
    chmod +x ${HOME}/minio-binaries/mc && \
    export PATH=${PATH}:${HOME}/minio-binaries/mc

COPY init.sh /usr/local/bin/init.sh
COPY avatar-policy.json /usr/local/bin/avatar-policy.json
COPY default-images/default_avatar.png /usr/local/bin/default_avatar.png
RUN chmod +x /usr/local/bin/init.sh

ENTRYPOINT ["/usr/local/bin/init.sh"]