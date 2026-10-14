// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EstimateActivity_ViewBinding implements Unbinder {
  private EstimateActivity target;

  @UiThread
  public EstimateActivity_ViewBinding(EstimateActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EstimateActivity_ViewBinding(EstimateActivity target, View source) {
    this.target = target;

    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
    target.labourLayout = Utils.findRequiredViewAsType(source, R.id.labourLayout, "field 'labourLayout'", LinearLayout.class);
    target.spareLayout = Utils.findRequiredViewAsType(source, R.id.spareLayout, "field 'spareLayout'", LinearLayout.class);
    target.totalEditText = Utils.findRequiredViewAsType(source, R.id.totalEditText, "field 'totalEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EstimateActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.labourRecyclerView = null;
    target.spareRecyclerView = null;
    target.labourLayout = null;
    target.spareLayout = null;
    target.totalEditText = null;
  }
}
