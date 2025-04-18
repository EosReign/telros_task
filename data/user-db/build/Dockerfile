FROM postgres:16-alpine3.20

RUN apk add --no-cache \
    bash \
    musl-locales \
    musl-locales-lang

ENV LANG=ru_RU.UTF-8 \
    LC_ALL=ru_RU.UTF-8 \
    LC_CTYPE=ru_RU.UTF-8

CMD ["postgres"]