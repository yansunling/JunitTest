@echo oFF

set file=%1
rem 判断文件是否存在
if exist %file% (del %file% && echo delete %file%) else (echo nofile)

set sql=%2
rem 对sql中的文件路径进行转义
rem set sql=%sql:\=/%

rem 测试sql使用
echo %sql%

cmd /k "C: && cd C:\mysql-5.6.26-winx64\bin && mysql -uroot -padmin -D bda2 -e %sql% && echo "success" & exit"






 
