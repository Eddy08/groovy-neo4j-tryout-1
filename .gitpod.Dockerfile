FROM gitpod/workspace-full

USER root
RUN curl -o /var/lib/apt/dazzle-marks/docker.gpg -fsSL https://download.docker.com/linux/debian/gpg  && apt-key add /var/lib/apt/dazzle-marks/docker.gpg  && add-apt-repository "deb[arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"  && apt-get update  && apt-get install -y docker-ce docker-ce-cli containerd.io

RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose  && chmod +x /usr/local/bin/docker-compose

USER gitpod