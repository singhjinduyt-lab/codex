export type Service = { id:string; title:string; subtitle:string; icon:string; gradient:string; offline:boolean; eta:string; badge?:string };
export type Activity = { id:string; label:string; meta:string; amount?:string; status:'Live'|'Done'|'Action' };
export type Product = { id:string; name:string; category:string; rating:number; price:string; image:string };
export const services: Service[] = [
 {id:'food',title:'Food Ordering',subtitle:'Restaurants, menus, cart, checkout, live tracking',icon:'🍜',gradient:'orange',offline:false,eta:'24 min',badge:'Hot'},
 {id:'grocery',title:'Grocery Delivery',subtitle:'Fresh daily baskets and scheduled delivery',icon:'🥬',gradient:'emerald',offline:true,eta:'35 min'},
 {id:'medicine',title:'Medicine Delivery',subtitle:'Pharmacy, reminders, prescriptions',icon:'💊',gradient:'cyan',offline:true,eta:'18 min'},
 {id:'taxi',title:'Taxi Booking',subtitle:'GPS rides, fare estimate, driver tracking',icon:'🚕',gradient:'amber',offline:false,eta:'3 min'},
 {id:'package',title:'Package Delivery',subtitle:'Courier pickup, proof of delivery, barcode scan',icon:'📦',gradient:'violet',offline:false,eta:'45 min'},
 {id:'hotel',title:'Hotel Booking',subtitle:'Rooms, reviews, maps, loyalty rates',icon:'🏨',gradient:'indigo',offline:true,eta:'Instant'},
 {id:'flight',title:'Flight Booking',subtitle:'Search, seats, boarding reminders',icon:'✈️',gradient:'blue',offline:true,eta:'Instant'},
 {id:'events',title:'Event Tickets',subtitle:'Concerts, sports, QR entry tickets',icon:'🎟️',gradient:'fuchsia',offline:true,eta:'Instant'},
 {id:'bills',title:'Recharge & Bills',subtitle:'Mobile, utilities, subscriptions',icon:'💳',gradient:'green',offline:true,eta:'Instant'},
 {id:'wallet',title:'Digital Wallet',subtitle:'Balance, send, receive, QR payments, history',icon:'👛',gradient:'slate',offline:true,eta:'Instant',badge:'Secure'},
 {id:'shop',title:'Marketplace',subtitle:'Products, wishlist, reviews, ratings',icon:'🛍️',gradient:'rose',offline:true,eta:'2 days'},
 {id:'chat',title:'Chat & Calls',subtitle:'Messaging, voice, images, video, live presence',icon:'💬',gradient:'teal',offline:false,eta:'Live'},
 {id:'community',title:'Community Feed',subtitle:'Posts, likes, comments and sharing',icon:'🌐',gradient:'purple',offline:true,eta:'Live'},
 {id:'ai',title:'AI Assistant',subtitle:'Chatbot, voice commands, recommendations',icon:'🤖',gradient:'ai',offline:false,eta:'Live'},
 {id:'learn',title:'Learning',subtitle:'Courses, videos, progress tracking',icon:'🎓',gradient:'learn',offline:true,eta:'Self-paced'},
 {id:'news',title:'News & Weather',subtitle:'Local news, weather and alerts',icon:'📰',gradient:'weather',offline:true,eta:'Updated'}
];
export const activities: Activity[] = [
 {id:'a1',label:'Sushi order arriving',meta:'Driver is 1.2 km away',amount:'$38.40',status:'Live'},
 {id:'a2',label:'Wallet top-up',meta:'Stripe • Visa ending 4242',amount:'+$120.00',status:'Done'},
 {id:'a3',label:'Doctor appointment',meta:'Tomorrow 9:30 AM • Video visit',status:'Action'},
 {id:'a4',label:'Flight price alert',meta:'NYC → SFO dropped 14%',amount:'$219',status:'Action'}
];
export const products: Product[] = [
 {id:'p1',name:'Premium grocery basket',category:'Grocery',rating:4.9,price:'$29',image:'🥑'},
 {id:'p2',name:'Noise cancelling buds',category:'Shopping',rating:4.8,price:'$89',image:'🎧'},
 {id:'p3',name:'City museum pass',category:'Events',rating:4.7,price:'$18',image:'🎟️'}
];
