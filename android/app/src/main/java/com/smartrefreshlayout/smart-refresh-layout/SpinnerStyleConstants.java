// 此处请修改为你的包名称
package com.smartrefreshlayout;

import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import java.util.HashMap;

public class SpinnerStyleConstants {

  public static final String TRANSLATE = "translate";
  public static final String FIX_BEHIND = "fixBehind";
  public static final String SCALE = "scale";
  public static final String FIX_FRONT = "fixFront";
  public static final String MATCH_LAYOUT = "matchLayout";

  public static final HashMap<String, SpinnerStyle> SpinnerStyleMap = new HashMap<String, SpinnerStyle>() {
    {
      put(TRANSLATE, SpinnerStyle.Translate);
      put(FIX_BEHIND, SpinnerStyle.FixedBehind);
      put(SCALE, SpinnerStyle.Scale);
      put(MATCH_LAYOUT, SpinnerStyle.MatchLayout);
      put(FIX_FRONT, SpinnerStyle.FixedFront);
    }
  };
}
