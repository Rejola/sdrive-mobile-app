// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RatingsActivity_ViewBinding implements Unbinder {
  private RatingsActivity target;

  @UiThread
  public RatingsActivity_ViewBinding(RatingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RatingsActivity_ViewBinding(RatingsActivity target, View source) {
    this.target = target;

    target.ratingRecyclerView = Utils.findRequiredViewAsType(source, R.id.ratingRecyclerView, "field 'ratingRecyclerView'", RecyclerView.class);
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RatingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ratingRecyclerView = null;
    target.noResultFound = null;
  }
}
