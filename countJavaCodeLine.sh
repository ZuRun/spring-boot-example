#!/usr/bin/env bash
# 统计java代码行数,忽略空行和//注释
# idea中可以拷贝下来在terminal中执行
find . -name "*.java"|xargs cat|grep -v -e ^$ -e ^\s*\/\/.*$|wc -l
