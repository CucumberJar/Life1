echo "Компиляция проекта..."
javac -d out src/*.java
if [ $? -ne 0 ]; then
    echo "Ошибка компиляции."
    exit 1
fi

# Запускаем программу
echo "Запуск симулятора экосистемы..."
java -cp out Main
