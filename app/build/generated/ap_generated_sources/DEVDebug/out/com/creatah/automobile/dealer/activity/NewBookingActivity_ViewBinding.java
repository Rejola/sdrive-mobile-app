// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewBookingActivity_ViewBinding implements Unbinder {
  private NewBookingActivity target;

  @UiThread
  public NewBookingActivity_ViewBinding(NewBookingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewBookingActivity_ViewBinding(NewBookingActivity target, View source) {
    this.target = target;

    target.searchText = Utils.findRequiredViewAsType(source, R.id.searchText, "field 'searchText'", EditText.class);
    target.searchIcon = Utils.findRequiredViewAsType(source, R.id.searchIcon, "field 'searchIcon'", ImageView.class);
    target.addNewButton = Utils.findRequiredViewAsType(source, R.id.addNewButton, "field 'addNewButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewBookingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchText = null;
    target.searchIcon = null;
    target.addNewButton = null;
  }
}
