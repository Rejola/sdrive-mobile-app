// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddSubSpareLabourActivity_ViewBinding implements Unbinder {
  private AddSubSpareLabourActivity target;

  private View view2131296884;

  @UiThread
  public AddSubSpareLabourActivity_ViewBinding(AddSubSpareLabourActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddSubSpareLabourActivity_ViewBinding(final AddSubSpareLabourActivity target,
      View source) {
    this.target = target;

    View view;
    target.spareLabourRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareLabourRecyclerView, "field 'spareLabourRecyclerView'", RecyclerView.class);
    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.nameTextView, "field 'titleTextView'", TextView.class);
    target.closeImage = Utils.findRequiredViewAsType(source, R.id.closeImage, "field 'closeImage'", ImageView.class);
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    view = Utils.findRequiredView(source, R.id.submitButton, "method 'onSubmitClicked'");
    view2131296884 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubmitClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddSubSpareLabourActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spareLabourRecyclerView = null;
    target.titleTextView = null;
    target.closeImage = null;
    target.noResultFound = null;

    view2131296884.setOnClickListener(null);
    view2131296884 = null;
  }
}
