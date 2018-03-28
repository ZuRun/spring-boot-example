# 统计java代码行数,忽略空行和//注释
find . -name "*.java"|xargs cat|grep -v -e ^$ -e ^\s*\/\/.*$|wc -l
