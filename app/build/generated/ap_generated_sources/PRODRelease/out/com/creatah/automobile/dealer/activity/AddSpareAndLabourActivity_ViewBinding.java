// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddSpareAndLabourActivity_ViewBinding implements Unbinder {
  private AddSpareAndLabourActivity target;

  private View view2131296803;

  private View view2131296801;

  private View view2131296942;

  @UiThread
  public AddSpareAndLabourActivity_ViewBinding(AddSpareAndLabourActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddSpareAndLabourActivity_ViewBinding(final AddSpareAndLabourActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.searchSpareAutoCompleteTextView, "field 'searchSpareAutoCompleteTextView' and method 'onSpareSearchLayout'");
    target.searchSpareAutoCompleteTextView = Utils.castView(view, R.id.searchSpareAutoCompleteTextView, "field 'searchSpareAutoCompleteTextView'", AutoCompleteTextView.class);
    view2131296803 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSpareSearchLayout();
      }
    });
    view = Utils.findRequiredView(source, R.id.searchLabourAutoCompleteTextView, "field 'labourAutoCompleteTextView' and method 'onLabourSearchLayout'");
    target.labourAutoCompleteTextView = Utils.castView(view, R.id.searchLabourAutoCompleteTextView, "field 'labourAutoCompleteTextView'", AutoCompleteTextView.class);
    view2131296801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLabourSearchLayout();
      }
    });
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
    target.addSpareLayout = Utils.findRequiredViewAsType(source, R.id.addSpareLayout, "field 'addSpareLayout'", LinearLayout.class);
    target.addLabourLayout = Utils.findRequiredViewAsType(source, R.id.addLabourLayout, "field 'addLabourLayout'", LinearLayout.class);
    target.descriptionLayout = Utils.findRequiredViewAsType(source, R.id.descriptionLayout, "field 'descriptionLayout'", LinearLayout.class);
    target.costLinearLayout = Utils.findRequiredViewAsType(source, R.id.totalLayout, "field 'costLinearLayout'", LinearLayout.class);
    target.labourCostTextView = Utils.findRequiredViewAsType(source, R.id.labourCostTextView, "field 'labourCostTextView'", TextView.class);
    target.spareCostTextView = Utils.findRequiredViewAsType(source, R.id.spareCostTextView, "field 'spareCostTextView'", TextView.class);
    target.labourCGSTextView = Utils.findRequiredViewAsType(source, R.id.labourCGSTTextView, "field 'labourCGSTextView'", TextView.class);
    target.spareCGSTTextView = Utils.findRequiredViewAsType(source, R.id.spareCGSTTextView, "field 'spareCGSTTextView'", TextView.class);
    target.labourSGSTextView = Utils.findRequiredViewAsType(source, R.id.labourSGSTTextView, "field 'labourSGSTextView'", TextView.class);
    target.spareSGSTTextView = Utils.findRequiredViewAsType(source, R.id.spareSGSTTextView, "field 'spareSGSTTextView'", TextView.class);
    target.totalTextView = Utils.findRequiredViewAsType(source, R.id.totalCostTextView, "field 'totalTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.updateButton, "field 'updateButton' and method 'onUpdateClicked'");
    target.updateButton = Utils.castView(view, R.id.updateButton, "field 'updateButton'", Button.class);
    view2131296942 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUpdateClicked();
      }
    });
    target.addSpareAndLabour = Utils.findRequiredViewAsType(source, R.id.addSpareAndLabour, "field 'addSpareAndLabour'", TextView.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddSpareAndLabourActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchSpareAutoCompleteTextView = null;
    target.labourAutoCompleteTextView = null;
    target.noResultFound = null;
    target.labourRecyclerView = null;
    target.spareRecyclerView = null;
    target.addSpareLayout = null;
    target.addLabourLayout = null;
    target.descriptionLayout = null;
    target.costLinearLayout = null;
    target.labourCostTextView = null;
    target.spareCostTextView = null;
    target.labourCGSTextView = null;
    target.spareCGSTTextView = null;
    target.labourSGSTextView = null;
    target.spareSGSTTextView = null;
    target.totalTextView = null;
    target.updateButton = null;
    target.addSpareAndLabour = null;
    target.retryButton = null;

    view2131296803.setOnClickListener(null);
    view2131296803 = null;
    view2131296801.setOnClickListener(null);
    view2131296801 = null;
    view2131296942.setOnClickListener(null);
    view2131296942 = null;
  }
}
