# LogiLed

![License](https://img.shields.io/badge/License-GPLv3-blue.svg) [![Releases](https://img.shields.io/github/downloads/developersu/LogiLed/total.svg)]() [![LatestVer](https://img.shields.io/github/release/developersu/LogiLed.svg)]()

[Support author](#support-this-app)

LogiLed is a Logitech G513 Carbon GUI driver for adjusting backlight and effects.

#### License

[GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.html)

#### Used libraries & resources
* [OpenJFX](https://wiki.openjdk.java.net/display/OpenJFX/Main)
* [usb4java](https://mvnrepository.com/artifact/org.usb4java/usb4java)
* [Jackson](https://github.com/FasterXML/jackson)
* Few icons taken from: [materialdesignicons.com](http://materialdesignicons.com/)
* Special thanks to pioneers and superstars who created, contributed and maintaining [g810-led](https://github.com/MatMoul/g810-led) project!

### System requirements

JRE/JDK 8u60 or higher.

### Usage in Linux

1. Install JRE/JDK 8u60 or higher (openJDK is good. Oracle's one is also good). JavaFX not needed (it's embedded).

2. `root # java -jar /path/to/application.jar`

3. Optional. Add user to 'udev' rules to use NS not-from-root-account
```
root # vim /etc/udev/rules.d/99-G513.rules
SUBSYSTEM=="usb", ATTRS{idVendor}=="046D", ATTRS{idProduct}=="c33c", GROUP="plugdev"
root # udevadm control --reload-rules && udevadm trigger
```

### Building

`$ mvn package`

## Support this app

If you like this app, just give a star. 

Want to support development? Make a donation* (see below):

[Yandex.Money](https://money.yandex.ru/to/410014301951665)

[PayPal](https://paypal.me/developersu)

*Legal information:

* [EN] Please note! This is non-commercial application. Any received money considered as a gift.
* [RU] Пожалуйста обратите внимание! Это некоммерческое приложение. Перечисляя средства вы совершаете дарение.

#### TODO

* [x] Tray support
    * [ ] tray icon size checks
* [x] Configuration files support
* [ ] Settings
    * [ ] Tray icon settings
    * [ ] Autoload
* [ ] Headless mode (CLI)
* [ ] Fix UI
    * [ ] Add opened file name to info pane
* [ ] Dark theme 