// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubSpareLabourAdapter$SpareLabourView_ViewBinding implements Unbinder {
  private SubSpareLabourAdapter.SpareLabourView target;

  @UiThread
  public SubSpareLabourAdapter$SpareLabourView_ViewBinding(SubSpareLabourAdapter.SpareLabourView target,
      View source) {
    this.target = target;

    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.item, "field 'titleTextView'", TextView.class);
    target.checkBox = Utils.findRequiredViewAsType(source, R.id.checkbox, "field 'checkBox'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubSpareLabourAdapter.SpareLabourView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleTextView = null;
    target.checkBox = null;
  }
}
