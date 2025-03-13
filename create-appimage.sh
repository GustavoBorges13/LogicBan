#!/bin/bash

# Download AppImageTool
wget https://github.com/AppImage/AppImageKit/releases/download/continuous/appimagetool-x86_64.AppImage
chmod +x appimagetool-x86_64.AppImage

# Create AppDir structure
mkdir -p AppDir/usr/bin
mkdir -p AppDir/usr/lib
mkdir -p AppDir/usr/share/applications
mkdir -p AppDir/usr/share/icons/hicolor/256x256/apps

# Copy the JAR file
cp target/logicban-1.0.0.jar AppDir/usr/bin/logicban.jar

# Create a launcher script
echo '#!/bin/sh' > AppDir/usr/bin/logicban
echo 'java -jar /usr/bin/logicban.jar' >> AppDir/usr/bin/logicban
chmod +x AppDir/usr/bin/logicban

# Create a desktop file
cat > AppDir/usr/share/applications/logicban.desktop <<EOF
[Desktop Entry]
Name=LogicBan
Exec=logicban
Icon=logicban
Type=Application
Categories=Utility;
EOF

# Copy the icon
cp src/resources/assets/logos/icone.ico AppDir/usr/share/icons/hicolor/256x256/apps/logicban.ico

# Extract AppImageTool (to avoid FUSE dependency)
./appimagetool-x86_64.AppImage --appimage-extract
mv squashfs-root appimagetool

# Create the AppImage using the extracted AppImageTool
./appimagetool/AppRun AppDir LogicBan.AppImage