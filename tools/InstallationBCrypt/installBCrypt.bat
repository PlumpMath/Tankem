@echo off

if exist "C:\Logiciels Portables\Panda3D-1.9.1-x64\python\Scripts\pip.exe" (
	echo pip deja installe!
) else (
	echo Installation de pip...
	"C:\Logiciels Portables\Panda3D-1.9.1-x64\python\python.exe" get-pip.py
)


if exist "C:\Logiciels Portables\Panda3D-1.9.1-x64\python\Lib\site-packages\bcrypt" (
    echo bcrypt deja installe!
) else (
    echo Installation de bcrypt...
	"C:\Logiciels Portables\Panda3D-1.9.1-x64\python\Scripts\pip" install wheel
	"C:\Logiciels Portables\Panda3D-1.9.1-x64\python\Scripts\pip" install py_bcrypt-0.4-cp27-none-win_amd64.whl
)
pause