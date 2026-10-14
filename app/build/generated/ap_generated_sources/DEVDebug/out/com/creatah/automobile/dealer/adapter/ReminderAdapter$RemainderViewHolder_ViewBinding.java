// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReminderAdapter$RemainderViewHolder_ViewBinding implements Unbinder {
  private ReminderAdapter.RemainderViewHolder target;

  @UiThread
  public ReminderAdapter$RemainderViewHolder_ViewBinding(ReminderAdapter.RemainderViewHolder target,
      View source) {
    this.target = target;

    target.serviceName = Utils.findRequiredViewAsType(source, R.id.serviceTextView, "field 'serviceName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReminderAdapter.RemainderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.serviceName = null;
  }
}
