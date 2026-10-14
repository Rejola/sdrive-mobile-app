// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InstantDetailsActivity_ViewBinding implements Unbinder {
  private InstantDetailsActivity target;

  @UiThread
  public InstantDetailsActivity_ViewBinding(InstantDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InstantDetailsActivity_ViewBinding(InstantDetailsActivity target, View source) {
    this.target = target;

    target.costTextView = Utils.findRequiredViewAsType(source, R.id.costTextView, "field 'costTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InstantDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.costTextView = null;
  }
}
