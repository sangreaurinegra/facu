
echo "ini stop all containers..."

docker stop  $(docker ps -q)

echo "end stop all containers..."
