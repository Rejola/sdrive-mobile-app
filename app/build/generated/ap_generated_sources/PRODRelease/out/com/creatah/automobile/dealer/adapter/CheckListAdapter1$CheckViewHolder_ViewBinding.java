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

public class CheckListAdapter1$CheckViewHolder_ViewBinding implements Unbinder {
  private CheckListAdapter1.CheckViewHolder target;

  @UiThread
  public CheckListAdapter1$CheckViewHolder_ViewBinding(CheckListAdapter1.CheckViewHolder target,
      View source) {
    this.target = target;

    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.item, "field 'titleTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckListAdapter1.CheckViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleTextView = null;
  }
}
