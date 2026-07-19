import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import Header from './component/Header';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AdminPanel from './component/admin/AdminPanel';
import CustomerDetails from './component/admin/CustomerDetails';
import ProfilePage from './component/customer/ProfilePage';
import AddressList from './component/customer/AddressList';
import Cart from './component/cart/Cart';
import DeleteUser from './component/customer/DeleteUser';
import SearchResults from './component/SearchResult';
import Footer from './component/Footer';
import { CartProvider } from './component/context/CartContext';
import PaymentList from './component/payment/PaymentList';
import SuccessfulPayments from './component/payment/SuccessfulPayments';
import UnsuccessfulPayments from './component/payment/UnsuccessfulPayments';
import PaymentDetails from './component/payment/PaymentDetails';
import PaymentForm from './component/payment/PaymentForm';
import CustomerPaymentsHistory from './component/payment/CustomerPaymentsHistory';
import OrdersDeliveredAdmin from './component/order/OrdersDeliveredAdmin';
import OrdersPendingAdmin from './component/order/OrdersPendingAdmin';
import OrderDetailsAdmin from './component/order/OrderDetailsAdmin';
import OrdersDeliveredCustomer from './component/order/OrdersDeliveredCustomer';
import OrdersInProcessCustomer from './component/order/OrdersInProcessCustomer';
import OrdersInProcessDetailsCust from './component/order/OrdersInProcessDetailsCust';
import OrderDeliveredDetailsCust from './component/order/OrderDeliveredDetailsCust';
import Home from './component/item/Home'
import GetByType from '../src/component/item/GetByType';
import AllProducts from './component/item/AllProducts';
import AllProductsForAdmin from './component/item/AllProductsForAdmin';
import ProductDetails from './component/item/ProductDetails';
import { UserProvider } from './component/context/UserContext';
import CustomerList from './component/admin/CustomerList';
import OrderSuccess from './component/order/OrderSucess';
import About from './component/item/About';


function App() {

  const isAdmin = false;

  return (
    <>
      <div className='App'>
        <UserProvider>
          <CartProvider>

            <BrowserRouter>
              <Header isAdmin={isAdmin} />
              <Routes>
                {/* <Route path="/" element={<AuthPage />} /> */}
                <Route path="/admin" element={<AdminPanel />} />
                <Route path="/customers" element={<CustomerList />} />
                <Route path="/" element={< Home />}></Route>
                <Route path="/items/:type" element={<GetByType />}></Route>
                <Route path="/items" element={<AllProducts />}></Route>
                <Route path="/allitemsadmin" element={<AllProductsForAdmin />}></Route>
                <Route path="/productdetail/:itemid" element={<ProductDetails />}></Route>
                <Route path="/customer/:customerId" element={<CustomerDetails />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/address" element={<AddressList />} />
                <Route path='/cart' element={<Cart />} />
                <Route path='/delete-user/:customerId' element={<DeleteUser />} />
                <Route path="/search-results" element={<SearchResults />} />
                <Route path='/cart' element={<Cart />} />
                <Route path='/admin/payment' element={<PaymentList />} />
                <Route path='/admin/payment/sucessful' element={<SuccessfulPayments />} />
                <Route path='/admin/payment/unsucessful' element={<UnsuccessfulPayments />} />
                <Route path='/payment/payment-details/:paymentId' element={<PaymentDetails />} />
                <Route path='/payments/createpayment/' element={<PaymentForm />} />
                <Route path='/customer/payment/:customerId' element={<CustomerPaymentsHistory />} />
                <Route path='/admin/orders' element={<OrdersDeliveredAdmin />} />
                <Route path='/admin/orders-pending' element={<OrdersPendingAdmin />} />
                <Route path='/admin/orders-details/:orderId' element={<OrderDetailsAdmin />} />
                <Route path='/customer/orders-delivered/:customerId' element={<OrdersDeliveredCustomer />} />
                <Route path='/customer/orders-inProcess/:customerId' element={<OrdersInProcessCustomer />} />
                <Route path='/customer/orders-details/inProcess/:orderId' element={<OrdersInProcessDetailsCust />} />
                <Route path='/customer/orders-details/delivered/:orderId' element={<OrderDeliveredDetailsCust />} />
                <Route path='/order-sucess' element={<OrderSuccess />} />
                <Route path='/about' element={<About />} />

              </Routes>
              <Footer />
            </BrowserRouter>

          </CartProvider>
        </UserProvider>
      </div>
    </>
  );
}

export default App;
