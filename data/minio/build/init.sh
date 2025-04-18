#!/bin/bash

# Запустите MinIO в фоновом режиме
minio server --console-address ":9001" /data &

# Ожидание запуска MinIO
echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Waiting"
until curl -s http://localhost:9000/minio/health/live; do
    echo "Waiting for MinIO to be ready..."
    sleep 10
done

echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Create Alias minio-container'a"
mc alias set minio http://localhost:9000 "$MINIO_ROOT_USER" "$MINIO_ROOT_PASSWORD"

# Функция создания бакета
create_bucket() {
  local bucket_name=$1
  if mc ls minio/$bucket_name > /dev/null 2>&1; then
    echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Bucket '$bucket_name' exists."
  else
    echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Bucket '$bucket_name' don't exists. Generating..."
    mc mb minio/$bucket_name || true
    # Добавление политики доступа к бакету
    echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Set anonymous policy to read-only"
    mc anonymous set download minio/$bucket_name
  fi
}

# Создание бакетов
echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Create buckets"
for bucket in avatar image; do
  create_bucket $bucket
done

#Добавление дефолтного изображения аватара пользователя
echo "$(date "+%Y:%m:%d %H:%M:%S") [INFO]: Adding default user image"
mc cp /usr/local/bin/default_avatar.png minio/avatar/default_avatar.png

# Задержите выполнение, чтобы контейнер не завершился
tail -f /dev/null