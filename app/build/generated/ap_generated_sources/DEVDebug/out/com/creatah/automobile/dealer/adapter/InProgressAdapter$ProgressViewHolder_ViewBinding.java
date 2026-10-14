// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InProgressAdapter$ProgressViewHolder_ViewBinding implements Unbinder {
  private InProgressAdapter.ProgressViewHolder target;

  @UiThread
  public InProgressAdapter$ProgressViewHolder_ViewBinding(InProgressAdapter.ProgressViewHolder target,
      View source) {
    this.target = target;

    target.acknowledgeLayout = Utils.findRequiredViewAsType(source, R.id.acknowledgeLayout, "field 'acknowledgeLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InProgressAdapter.ProgressViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.acknowledgeLayout = null;
  }
}
