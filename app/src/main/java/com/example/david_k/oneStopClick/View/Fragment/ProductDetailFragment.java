package com.example.david_k.oneStopClick.View.Fragment;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductLike;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.ProductDetail.ProductDetailActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    public static final String TAG = "ProductDetailFragment";


    TextView itemOrderedEditText;
    ImageButton plusItemCartButton;
    ImageButton minusItemCartButton;
    ImageButton addToCartButton;
    private ProductDetailActivity.CartAddedDialogFragment cartAddedDialog;

    Product product;
    FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();
    private boolean isLiked = false;
    private boolean isNewCart;
    private int numItemOrdered;
    private YouTubePlayer mYoutubePlayer;
    private String mVideoId = "qQrP_GPn1ic";

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            product = getArguments().getParcelable(Constants.productKey);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        isNewCart = true;
        setupUI(view);

        return view;
    }

    private void setupUI(View view) {
        TextView nameTv = view.findViewById(R.id.product_detail_name);
        nameTv.setText(product.getName());

        TextView priceTv = view.findViewById(R.id.product_detail_price);
        priceTv.setText(String.valueOf(product.getPrice()) + " USD");

        ImageView productPhoto = view.findViewById(R.id.product_detail_image);
        firebaseProviderHelper.setupProductPhoto(getActivity(), product.getImageName(), productPhoto);

        TextView viewCountTv = view.findViewById(R.id.product_detail_view_count);
        viewCountTv.setText("Views: " + String.valueOf(product.getViewCount()));

        FrameLayout likeFrameLayout = view.findViewById(R.id.product_detail_frame_like);
        ImageView likeIcon = view.findViewById(R.id.product_detail_like_icon);

        TextView likeLabelTv = view.findViewById(R.id.product_detail_like_label);

        TextView likeCountTv = view.findViewById(R.id.product_detail_like_count);
        likeCountTv.setText(String.valueOf(product.getLikeCount()));

        likeFrameLayout.setOnClickListener(viewFrame -> {
            DatabaseReference likeProductRef = FirebaseProvider.getCurrentProvider().getProductDBReference()
                    .child(product.getFirebaseKey());

            firebaseProviderHelper.getDataSnapshotOnceFromDBRef(likeProductRef, new OnGetDataListener() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(DataSnapshot data) {
                    setupUIForLikeFrame(data, likeIcon, likeLabelTv, likeCountTv);
                }

                @Override
                public void onFailed(DatabaseError databaseError) { }
            });
        });

        setupLikeIconValue(likeIcon, likeLabelTv);

        ImageView shareIcon = view.findViewById(R.id.product_detail_share_icon);
        shareIcon.setOnClickListener(viewShare -> {
            setupShareActionActivity();
        });

//        WebView webView = view.findViewById(R.id.product_detail_trailler_webview);
//        setupUIForTrailerWebview(webView);
        initializeYoutubeFragment();

        itemOrderedEditText = view.findViewById(R.id.product_detail_order_item_text);
        plusItemCartButton = view.findViewById(R.id.product_detail_plus_cart_button);
        minusItemCartButton = view.findViewById(R.id.product_detail_minus_cart_button);
        addToCartButton = view.findViewById(R.id.product_detail_add_to_cart_button);

        plusItemCartButton.setOnClickListener(v -> {
            numItemOrdered++;

            setTextForItemOrdered();
        });

        minusItemCartButton.setOnClickListener(v -> {
            numItemOrdered--;

            setTextForItemOrdered();
        });

        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "added to cart", Toast.LENGTH_SHORT).show();

            // Add / Update Cart Order
            String userId = firebaseProviderHelper.getUserId();
            firebaseProviderHelper.setOrderQtyForProductCart(isNewCart, numItemOrdered, product.getFirebaseKey(), userId);

//                cartAddedDialog = new ProductDetailActivity.CartAddedDialogFragment();
//                cartAddedDialog.show(getSupportFragmentManager(), "CartAddedDialogFragment");
        });

        setOrderQtyByProductKey(product.getFirebaseKey());
    }

    private void setupShareActionActivity() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Find " + product.getName() + " at " + String.valueOf(product.getPrice()) + " USD. https://onstopclick.com/product/" + product.getFirebaseKey();
        intent.putExtra(Intent.EXTRA_SUBJECT, "One Stop Click");
        intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    private void setupUIForLikeFrame(DataSnapshot data, ImageView likeIcon, TextView likeLabelTv, TextView likeCountTv) {
        int likeCount = data.getValue(Product.class).getLikeCount();
        if (!isLiked) {
            isLiked = true;
            likeCount += 1;
            Toast.makeText(getActivity(), "Liked", Toast.LENGTH_SHORT).show();
            likeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_full));
            likeLabelTv.setText("Liked");
            likeCountTv.setText(String.valueOf(likeCount));

            Log.d(TAG, "setupUI: like from user Id : " + firebaseProviderHelper.getUserId());
        } else {
            isLiked = false;
            likeCount -= 1;
            likeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_border));
            Toast.makeText(getActivity(), "Unlike", Toast.LENGTH_SHORT).show();
            likeLabelTv.setText("Like");
            likeCountTv.setText(String.valueOf(likeCount));

            Log.d(TAG, "setupUI: UNlike from user Id : " + firebaseProviderHelper.getUserId());
        }

        // update Is liked val firebase DB
        firebaseProviderHelper.setIsLikedForProductLike(isLiked, product.getFirebaseKey(), firebaseProviderHelper.getUserId());
        firebaseProviderHelper.setLikeCountForProduct(likeCount, product.getFirebaseKey());
    }

    private void setupLikeIconValue(ImageView likeIcon, TextView likeLabelTv) {
        DatabaseReference likeFromUser = FirebaseProvider.getCurrentProvider().getProductLikeDBReference()
                .child(firebaseProviderHelper.getUserId());

        firebaseProviderHelper.getDataSnapshotOnceFromDBRef(likeFromUser, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot dataItem: data.getChildren()) {
                    ProductLike productLike = dataItem.getValue(ProductLike.class);
                    if (productLike != null && productLike.getProductKey().equals(product.getFirebaseKey())) {
                        if (productLike.getIsLiked()) {
                            likeLabelTv.setText("Liked");
                            likeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_full));
                            isLiked = true;
                            break;
                        }
                        else {
                            likeLabelTv.setText("Like");
                            likeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_border));
                            isLiked = false;
                        }
                    } else {
                        likeLabelTv.setText("Like");
                        likeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_border));
                        isLiked = false;
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    //Call this method after getting mVideoId
    private void initializeYoutubeFragment() {

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youTubePlayerFragment.initialize(getString(R.string.youtube_key), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    mYoutubePlayer = player;
                    mYoutubePlayer.setShowFullscreenButton(false);
                    mYoutubePlayer.cueVideo(mVideoId);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {


            }
        });
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.product_detail_youtube_view, youTubePlayerFragment).commit();
    }

    private void setTextForItemOrdered(){
        if (numItemOrdered < 0) {
            numItemOrdered = 0;
        }

        itemOrderedEditText.setText(String.valueOf(numItemOrdered));
    }

    private void setOrderQtyByProductKey(String productKey){

        String userId = firebaseProviderHelper.getUserId();
        Query query = FirebaseProvider.getCurrentProvider().getProductCartDBReference()
                .child(userId)
                .orderByChild(ProductCart.COLUMN_PRODUCT_KEY).equalTo(productKey);

        firebaseProviderHelper.getDataSnapshotOnceFromQuery(query, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                if (!data.hasChildren()){
                    numItemOrdered = 0;
                    isNewCart = true;
                }

                for (DataSnapshot itemData : data.getChildren()) {
                    numItemOrdered = itemData.child(ProductCart.COLUMN_ORDER_QTY).getValue(int.class);
                    isNewCart = false;
                    break;
                }

                setTextForItemOrdered();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.w(TAG, "Failed to get product cart : " + databaseError.getMessage(), null);
            }
        });
    }
}
