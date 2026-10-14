// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BillAmountActivity_ViewBinding implements Unbinder {
  private BillAmountActivity target;

  @UiThread
  public BillAmountActivity_ViewBinding(BillAmountActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BillAmountActivity_ViewBinding(BillAmountActivity target, View source) {
    this.target = target;

    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.labourTextView = Utils.findRequiredViewAsType(source, R.id.labouTextView, "field 'labourTextView'", TextView.class);
    target.labourGstCost = Utils.findRequiredViewAsType(source, R.id.labourCGstCost, "field 'labourGstCost'", TextView.class);
    target.spareTextView = Utils.findRequiredViewAsType(source, R.id.spareTextView, "field 'spareTextView'", TextView.class);
    target.totalTextView = Utils.findRequiredViewAsType(source, R.id.totalTextView, "field 'totalTextView'", TextView.class);
    target.billLayout = Utils.findRequiredViewAsType(source, R.id.billLayout, "field 'billLayout'", LinearLayout.class);
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    target.spareLayout = Utils.findRequiredViewAsType(source, R.id.addSpareLayout, "field 'spareLayout'", LinearLayout.class);
    target.labourLayout = Utils.findRequiredViewAsType(source, R.id.addLabourLayout, "field 'labourLayout'", LinearLayout.class);
    target.sGstLabourText = Utils.findRequiredViewAsType(source, R.id.sGstLabourText, "field 'sGstLabourText'", TextView.class);
    target.sGStspareCostTextView = Utils.findRequiredViewAsType(source, R.id.sGStspareCostTextView, "field 'sGStspareCostTextView'", TextView.class);
    target.spareCGstCost = Utils.findRequiredViewAsType(source, R.id.spareCGstCost, "field 'spareCGstCost'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BillAmountActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spareRecyclerView = null;
    target.labourRecyclerView = null;
    target.labourTextView = null;
    target.labourGstCost = null;
    target.spareTextView = null;
    target.totalTextView = null;
    target.billLayout = null;
    target.noResultFound = null;
    target.spareLayout = null;
    target.labourLayout = null;
    target.sGstLabourText = null;
    target.sGStspareCostTextView = null;
    target.spareCGstCost = null;
  }
}
