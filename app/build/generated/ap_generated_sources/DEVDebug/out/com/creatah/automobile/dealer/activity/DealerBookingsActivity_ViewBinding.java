// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DealerBookingsActivity_ViewBinding implements Unbinder {
  private DealerBookingsActivity target;

  private View view2131296392;

  private View view2131296454;

  @UiThread
  public DealerBookingsActivity_ViewBinding(DealerBookingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DealerBookingsActivity_ViewBinding(final DealerBookingsActivity target, View source) {
    this.target = target;

    View view;
    target.searchEditText = Utils.findRequiredViewAsType(source, R.id.searchEditText, "field 'searchEditText'", EditText.class);
    target.searchImageView = Utils.findRequiredViewAsType(source, R.id.searchIcon, "field 'searchImageView'", AppCompatImageView.class);
    target.searchLayout = Utils.findRequiredViewAsType(source, R.id.searchLayout, "field 'searchLayout'", LinearLayout.class);
    target.adviserSpinner = Utils.findRequiredViewAsType(source, R.id.adviserSpinner, "field 'adviserSpinner'", Spinner.class);
    target.frameLayout = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'frameLayout'", FrameLayout.class);
    target.footerLayout = Utils.findRequiredViewAsType(source, R.id.footerLayout, "field 'footerLayout'", LinearLayout.class);
    target.serviceInprogressLayout = Utils.findRequiredViewAsType(source, R.id.serviceInprogressLayout, "field 'serviceInprogressLayout'", LinearLayout.class);
    target.noJObCardFound = Utils.findRequiredViewAsType(source, R.id.noJObCardFound, "field 'noJObCardFound'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.noInternetConnection = Utils.findRequiredViewAsType(source, R.id.noInternetConnection, "field 'noInternetConnection'", TextView.class);
    view = Utils.findRequiredView(source, R.id.applyFilter, "method 'onApplyFilter'");
    view2131296392 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onApplyFilter();
      }
    });
    view = Utils.findRequiredView(source, R.id.clearButton, "method 'onClearFilter'");
    view2131296454 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClearFilter();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DealerBookingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchEditText = null;
    target.searchImageView = null;
    target.searchLayout = null;
    target.adviserSpinner = null;
    target.frameLayout = null;
    target.footerLayout = null;
    target.serviceInprogressLayout = null;
    target.noJObCardFound = null;
    target.swipeRefreshLayout = null;
    target.retryButton = null;
    target.progressLayout = null;
    target.noInternetConnection = null;

    view2131296392.setOnClickListener(null);
    view2131296392 = null;
    view2131296454.setOnClickListener(null);
    view2131296454 = null;
  }
}
