function input() {
  for var in "$@"
  do
    text=${var/ /%s}
    adb shell input text $text
    adb shell input keyevent 66
  done
}
