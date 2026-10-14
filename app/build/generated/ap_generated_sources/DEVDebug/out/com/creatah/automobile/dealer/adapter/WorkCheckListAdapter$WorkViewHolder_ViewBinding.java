// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WorkCheckListAdapter$WorkViewHolder_ViewBinding implements Unbinder {
  private WorkCheckListAdapter.WorkViewHolder target;

  @UiThread
  public WorkCheckListAdapter$WorkViewHolder_ViewBinding(WorkCheckListAdapter.WorkViewHolder target,
      View source) {
    this.target = target;

    target.descriptionEditText = Utils.findRequiredViewAsType(source, R.id.descriptionEditText, "field 'descriptionEditText'", TextView.class);
    target.spareIconCheck = Utils.findRequiredViewAsType(source, R.id.spareIcon, "field 'spareIconCheck'", ImageView.class);
    target.serviceCompletedIcon = Utils.findRequiredViewAsType(source, R.id.serviceCompletedIcon, "field 'serviceCompletedIcon'", ImageView.class);
    target.quantitytext = Utils.findRequiredViewAsType(source, R.id.quantity, "field 'quantitytext'", EditText.class);
    target.selectItem = Utils.findRequiredViewAsType(source, R.id.selectItem, "field 'selectItem'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WorkCheckListAdapter.WorkViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.descriptionEditText = null;
    target.spareIconCheck = null;
    target.serviceCompletedIcon = null;
    target.quantitytext = null;
    target.selectItem = null;
  }
}
