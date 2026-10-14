// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuickSearch_ViewBinding implements Unbinder {
  private QuickSearch target;

  private View view2131296803;

  private View view2131296801;

  @UiThread
  public QuickSearch_ViewBinding(QuickSearch target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QuickSearch_ViewBinding(final QuickSearch target, View source) {
    this.target = target;

    View view;
    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
    target.addSpareAndLabour = Utils.findRequiredViewAsType(source, R.id.addSpareAndLabour, "field 'addSpareAndLabour'", TextView.class);
    target.addSpareLayout = Utils.findRequiredViewAsType(source, R.id.addSpareLayout, "field 'addSpareLayout'", LinearLayout.class);
    target.totalTextView = Utils.findRequiredViewAsType(source, R.id.totalCostTextView, "field 'totalTextView'", TextView.class);
    target.spareCostTextView = Utils.findRequiredViewAsType(source, R.id.spareCostTextView, "field 'spareCostTextView'", TextView.class);
    target.labourCGSTextView = Utils.findRequiredViewAsType(source, R.id.labourCGSTTextView, "field 'labourCGSTextView'", TextView.class);
    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.spareCGSTTextView = Utils.findRequiredViewAsType(source, R.id.spareCGSTTextView, "field 'spareCGSTTextView'", TextView.class);
    target.labourSGSTextView = Utils.findRequiredViewAsType(source, R.id.labourSGSTTextView, "field 'labourSGSTextView'", TextView.class);
    target.spareSGSTTextView = Utils.findRequiredViewAsType(source, R.id.spareSGSTTextView, "field 'spareSGSTTextView'", TextView.class);
    target.labourCostTextView = Utils.findRequiredViewAsType(source, R.id.labourCostTextView, "field 'labourCostTextView'", TextView.class);
    target.costLinearLayout = Utils.findRequiredViewAsType(source, R.id.totalLayout, "field 'costLinearLayout'", LinearLayout.class);
    target.addLabourLayout = Utils.findRequiredViewAsType(source, R.id.addLabourLayout, "field 'addLabourLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.searchSpareAutoCompleteTextView, "method 'onSpareSearchLayout'");
    view2131296803 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSpareSearchLayout();
      }
    });
    view = Utils.findRequiredView(source, R.id.searchLabourAutoCompleteTextView, "method 'onLabourSearchLayout'");
    view2131296801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLabourSearchLayout();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    QuickSearch target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spareRecyclerView = null;
    target.addSpareAndLabour = null;
    target.addSpareLayout = null;
    target.totalTextView = null;
    target.spareCostTextView = null;
    target.labourCGSTextView = null;
    target.labourRecyclerView = null;
    target.spareCGSTTextView = null;
    target.labourSGSTextView = null;
    target.spareSGSTTextView = null;
    target.labourCostTextView = null;
    target.costLinearLayout = null;
    target.addLabourLayout = null;

    view2131296803.setOnClickListener(null);
    view2131296803 = null;
    view2131296801.setOnClickListener(null);
    view2131296801 = null;
  }
}
