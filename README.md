# LoopifyDJ

```java
public void your(int position, int id) {

  String[] colors = new String[]{"#0dc75a", "#f90b56", "#f3b919", "#7f00d9", "#1acbf7", "#ff74b7"};

  colorPicker.setColors(colors);
  //colorPicker.showTitle(false);
  //colorPicker.setCancelable(false);
  colorPicker.openColorPicker();

  colorPicker.setColorPickerListener(color -> {

      //HANDLE
      colorPicker.setDismiss();

  });```

}
