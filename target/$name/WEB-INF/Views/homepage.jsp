<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Tailwind Starter Template - Nordic Shop: Tailwind Toolbox</title>
    <meta name="description" content="Free open source Tailwind CSS Store template">
    <meta name="keywords"
        content="tailwind,tailwindcss,tailwind css,css,starter template,free template,store template, shop layout, minimal, monochrome, minimalistic, theme, nordic">
    <link href="resources/flowbite/flowbite.min.css" rel="stylesheet" />
    <!-- <link rel="stylesheet" href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" /> -->

    <link href="https://fonts.googleapis.com/css?family=Work+Sans:200,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="resources/bootstrap/bootstrap_icons.css" />
    <link rel="stylesheet" href="resources/swiper/swiper_bundle_min.css">
    <link rel="stylesheet" href="resources/style/output.css">
</head>

<body class="text-base leading-normal tracking-normal text-gray-600 bg-white work-sans">


    <section class="">
        <!-- side bar -->
        <div
            class="sidebar absolute  text-maintheme md:bg-pink-600 sm:flex md:hidden  z-40  top-0 bottom-0 lg:left-0 p-2 w-[180px] text-center bg-gray-900">
            <div class="text-xl ">
                <div class="p-2.5 mt-1 flex items-center">

                    <h1 class="font-bold text-maintheme text-[15px] ml-1">FIXMIA</h1>
                    <i class="cursor-pointer bi bi-x ml-14 lg:hidden" onclick="openSidebar()"></i>
                    <span class="z-50 hidden ml-5 text-4xl text-white cursor-pointer lg:flex left-16"
                        onclick="openSidebar()">
                        <i class="px-2 rounded-md bi bi-filter-left"></i>
                    </span>
                </div>
                <div class="my-2 bg-gray-600 h-[1px]"></div>
            </div>
            <div class="p-2.5 flex items-center rounded-md px-4 duration-300 cursor-pointer bg-gray-700 text-white">
                <i class="text-sm bi bi-search"></i>
                <input type="text" placeholder="Search"
                    class="text-[15px] ml-4 w-full bg-transparent focus:outline-none" />
            </div>
            <div
                class="p-2.5 mt-3 flex items-center rounded-md px-4 duration-300 cursor-pointer hover:bg-blue-600 text-white">
                <i class="bi bi-house-door-fill"></i>
                <span class="text-[15px] ml-4 text-gray-200 font-bold">Discover</span>
            </div>
            <div
                class="p-2.5 mt-3 flex items-center rounded-md px-4 duration-300 cursor-pointer hover:bg-blue-600 text-white">
                <i class="bi bi-bookmark-fill"></i>
                <span class="text-[15px] ml-4 text-gray-200 font-bold">Home</span>
            </div>
            <div class="my-4 bg-gray-600 h-[1px]"></div>
            <div class="p-2.5 mt-3 flex items-center rounded-md px-4 duration-300 cursor-pointer hover:bg-blue-600 text-white"
                onclick="dropdown()">
                <i class="bi bi-chat-left-text-fill"></i>
                <div class="flex items-center justify-between w-full">
                    <span class="text-[15px] ml-4 text-gray-200 font-bold">Chatbox</span>
                    <span class="text-sm rotate-180" id="arrow">
                        <i class="bi bi-chevron-down"></i>
                    </span>
                </div>
            </div>
            <div class="w-4/5 mx-auto mt-2 text-sm font-bold text-left text-gray-200" id="submenu">
                <h1 class="p-2 mt-1 rounded-md cursor-pointer hover:bg-blue-600">
                    Social
                </h1>
                <h1 class="p-2 mt-1 rounded-md cursor-pointer hover:bg-blue-600">
                    Personal
                </h1>
                <h1 class="p-2 mt-1 rounded-md cursor-pointer hover:bg-blue-600">
                    Friends
                </h1>
            </div>
            <div
                class="p-2.5 mt-3 flex items-center rounded-md px-4 duration-300 cursor-pointer hover:bg-blue-600 text-white">
                <i class="bi bi-box-arrow-in-right"></i>
                <span class="text-[15px] ml-4 tyou put declared them as postion absext-gray-200 font-bold">Logout</span>
            </div>
        </div>
        <!-- content -->
        <div class="flex flex-col">

            <!-- navbar -->
            <nav id="header" class="z-30 w-full py-1 sm:hidden md:flex">
                <div class="container flex flex-wrap items-center justify-between w-full px-6 py-3 mx-auto mt-0">

                    <label onclick="openSidebar()" for="menu-toggle" class="block cursor-pointer md:hidden">
                        <svg class="font-semibold fill-current" xmlns="http://www.w3.org/2000/svg" width="20"
                            height="20" viewBox="0 0 20 20">
                            <title>menu</title>
                            <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"></path>
                        </svg>
                    </label>
                    <input class="hidden" type="checkbox" id="menu-toggle" />

                    <div class="order-3 hidden w-full font-semibold text-gray-600 md:flex md:items-center md:w-auto md:order-1" id="menu">
                        <nav>
                            <ul class="items-center justify-between pt-4 text-base md:flex md:pt-0">
                                <li>
                                    <a class="inline-block px-4 py-2 no-underline hover:text-black hover:underline">
                                        <span
                                            class="z-50 hidden ml-5 text-4xl cursor-pointer text-text lg:flex left-16">

                                        </span>
                                    </a>
                                </li>
                                <li><a class="hidden px-4 py-2 no-underline md:inline-block hover:text-black hover:underline"
                                        href="#">Discover</a></li>
                                <li><a class="hidden px-4 py-2 no-underline md:inline-block hover:text-black hover:underline"
                                        href="#">About</a></li>
                            </ul>
                        </nav>
                    </div>

                    <div class="flex order-1 md:order-2">
                        <a class="flex items-center text-xl font-bold tracking-wide no-underline text-main-blue text-maintheme gap-x-2 hover:no-underline "
                            href="#">
                            <img width="24" height="24" src="resources/image_resources/logo.png" alt="" srcset="">
                            FIXMIA
                        </a>
                    </div>

                    <div class="flex items-center order-2 gap-x-2 md:order-3" id="nav-content">
                        <div class="hidden md:flex">
                            <div class="relative flex-wrap items-stretch hidden w-full ">
                                <input type="search"
                                    class="relative m-0 -mr-0.5 block w-[100px] min-w-0 flex-auto rounded-l border border-solid border-neutral-300 bg-transparent bg-clip-padding px-3 py-[0.10] text-base font-normal leading-[1.6] text-neutral-700 outline-none transition duration-200 ease-in-out focus:z-[3] focus:border-primary focus:text-neutral-700 focus:shadow-[inset_0_0_0_1px_rgb(59,113,202)] focus:outline-none dark:border-neutral-600 dark:text-neutral-200 dark:placeholder:text-neutral-200 dark:focus:border-primary"
                                    placeholder="Search" aria-label="Search" aria-describedby="button-addon1" />


                                <button
                                    class="relative z-[2] flex items-center rounded-r bg-primary px-6 py-2.5 text-xs font-medium uppercase leading-tight text-maintheme shadow-md transition duration-150 ease-in-out hover:bg-primary-700 hover:shadow-lg focus:bg-primary-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-primary-800 active:shadow-lg"
                                    type="button" id="button-addon1" data-te-ripple-init data-te-ripple-color="light">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                                        class="w-5 h-5">
                                        <path fill-rule="evenodd"
                                            d="M9 3.5a5.5 5.5 0 100 11 5.5 5.5 0 000-11zM2 9a7 7 0 1112.452 4.391l3.328 3.329a.75.75 0 11-1.06 1.06l-3.329-3.328A7 7 0 012 9z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </button>
                            </div>
                            <div class="relative flex items-center w-full h-12 overflow-hidden ">
                                <div class="grid w-12 h-full text-gray-600 cursor-pointer place-items-center">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" fill="none"
                                        viewBox="0 0 24 24" stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                                    </svg>
                                </div>

                                <input class="w-full h-full pr-2 text-sm text-gray-700 outline-none peer" type="text"
                                    id="search" placeholder="Search something.." />
                            </div>
                        </div>
                        <!-- user -->
                        <a class="inline-block no-underline hover:text-black" href="#">
                            <svg class="fill-current hover:text-black" xmlns="http://www.w3.org/2000/svg" width="24"
                                height="24" viewBox="0 0 24 24">
                                <circle fill="none" cx="12" cy="7" r="3" />
                                <path
                                    d="M12 2C9.243 2 7 4.243 7 7s2.243 5 5 5 5-2.243 5-5S14.757 2 12 2zM12 10c-1.654 0-3-1.346-3-3s1.346-3 3-3 3 1.346 3 3S13.654 10 12 10zM21 21v-1c0-3.859-3.141-7-7-7h-4c-3.86 0-7 3.141-7 7v1h2v-1c0-2.757 2.243-5 5-5h4c2.757 0 5 2.243 5 5v1H21z" />
                            </svg>
                        </1>
                        <!-- cart -->
                        <a class="inline-block pl-3 no-underline hover:text-black" href="#">
                            <svg class="fill-current hover:text-black" xmlns="http://www.w3.org/2000/svg" width="24"
                                height="24" viewBox="0 0 24 24">
                                <path
                                    d="M21,7H7.462L5.91,3.586C5.748,3.229,5.392,3,5,3H2v2h2.356L9.09,15.414C9.252,15.771,9.608,16,10,16h8 c0.4,0,0.762-0.238,0.919-0.606l3-7c0.133-0.309,0.101-0.663-0.084-0.944C21.649,7.169,21.336,7,21,7z M17.341,14h-6.697L8.371,9 h11.112L17.341,14z" />
                                <circle cx="10.5" cy="18.5" r="1.5" />
                                <circle cx="17.5" cy="18.5" r="1.5" />
                            </svg>
                        </a>

                    </div>
                </div>
            </nav>
            <!-- category slide -->
            <section class="flex justify-center w-full pb-2">
                <div class="container relative ">
                    <div
                        class="absolute top-[50%] w-[100%]  z-30  transform translate-y-[-50%] flex flex-row justify-between ">
                        <button id="prev-btn" class="p-2 mr-5 bg-white border border-gray-600 rounded-full shadow-lg">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="w-3 h-3 lg:w-4 lg:h-4">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M6.75 15.75L3 12m0 0l3.75-3.75M3 12h18" />
                            </svg>

                        </button>
                        <button id="next-btn" class="p-2 ml-5 bg-white border border-gray-600 rounded-full shadow-lg ">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="w-3 h-3 lg:w-4 lg:h-4">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M17.25 8.25L21 12m0 0l-3.75 3.75M21 12H3" />
                            </svg>

                        </button>
                    </div>

                    <div id="mySwiper2" class="px-12 swiper mySwiper">
                        <div class="swiper-wrapper">
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 1</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 2</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 3</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 4</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 5</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 6</span>
                                </div>
                            </li>
                            <li class="p-1 swiper-slide">
                                <div class="h-full p-2 border rounded-lg border-lg">
                                    <span>Carpenter 6</span>
                                </div>
                            </li>
                        </div>



                    </div>
                </div>
            </section>

            <div id="default-carousel" class="relative w-full" data-carousel="static">
                <!-- Carousel wrapper -->
                <div class="relative h-56 overflow-hidden rounded-lg md:h-96">
                    <!-- Item 1 -->
                    <div class="hidden duration-[1s] ease-in-out" data-carousel-item>
                        <img src="resources/image_resources/constructors.jpg"
                            class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
                    </div>
                    <!-- Item 2 -->
                    <div class="hidden duration-[1s] ease-in-out" data-carousel-item>
                        <img src="resources/image_resources/carpentors.jpg"
                            class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
                        <!-- <img src="/docs/images/carousel/carousel-2.svg" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="..."> -->
                    </div>
                    <!-- Item 3 -->
                    <div class="hidden duration-[1s] ease-in-out" data-carousel-item>
                        <img src="resources/image_resources/electrician.jpeg"
                            class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
                        <!-- <img src="/docs/images/carousel/carousel-3.svg" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="..."> -->
                    </div>
                    <!-- Item 4 -->
                    <div class="hidden duration-[1s] ease-in-out" data-carousel-item>
                        <img src="resources/image_resources/plumbers.jpeg"
                            class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
                        <!-- <img src="/docs/images/carousel/carousel-4.svg" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="..."> -->
                    </div>
                    <!-- Item 5 -->
                    <div class="hidden duration-[1s] ease-in-out" data-carousel-item>
                        <img src="resources/image_resources/computer_worker.png"
                            class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
                    </div>
                </div>
                <!-- Slider indicators -->
                <div class="absolute z-30 flex space-x-3 -translate-x-1/2 bottom-5 left-1/2">
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="true" aria-label="Slide 1"
                        data-carousel-slide-to="0"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 2"
                        data-carousel-slide-to="1"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 3"
                        data-carousel-slide-to="2"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 4"
                        data-carousel-slide-to="3"></button>
                    <button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 5"
                        data-carousel-slide-to="4"></button>
                </div>
                <!-- Slider controls -->
                <button type="button"
                    class="absolute top-0 left-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
                    data-carousel-prev>
                    <span
                        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                        <svg class="w-4 h-4 text-white dark:text-gray-800" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M5 1 1 5l4 4" />
                        </svg>
                        <span class="sr-only">Previous</span>
                    </span>
                </button>
                <button type="button"
                    class="absolute top-0 right-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
                    data-carousel-next>
                    <span
                        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
                        <svg class="w-4 h-4 text-white dark:text-gray-800" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="m1 9 4-4-4-4" />
                        </svg>
                        <span class="sr-only">Next</span>
                    </span>
                </button>
            </div>

            <!-- content/product list -->
            <div class="container flex flex-wrap items-center pt-4 pb-12 mx-auto">



                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1555982105-d25af4182e4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1508423134147-addf71308178?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1449247709967-d4461a6a6103?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/reserve/LJIZlzHgQ7WPSh5KVTCB_Typewriter.jpg?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1467949576168-6ce8e2df4e13?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1544787219-7f47ccb76574?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1550837368-6594235de85c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

                <div class="flex flex-col w-full p-6 md:w-1/3 xl:w-1/4">
                    <a href="#">
                        <img class="hover:grow hover:shadow-lg"
                            src="https://images.unsplash.com/photo-1551431009-a802eeec77b1?ixlib=rb-1.2.1&auto=format&fit=crop&w=400&h=400&q=80">
                        <div class="flex items-center justify-between pt-3">
                            <p class="">Product Name</p>
                            <svg class="w-6 h-6 text-gray-500 fill-current hover:text-black"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                <path
                                    d="M12,4.595c-1.104-1.006-2.512-1.558-3.996-1.558c-1.578,0-3.072,0.623-4.213,1.758c-2.353,2.363-2.352,6.059,0.002,8.412 l7.332,7.332c0.17,0.299,0.498,0.492,0.875,0.492c0.322,0,0.609-0.163,0.792-0.409l7.415-7.415 c2.354-2.354,2.354-6.049-0.002-8.416c-1.137-1.131-2.631-1.754-4.209-1.754C14.513,3.037,13.104,3.589,12,4.595z M18.791,6.205 c1.563,1.571,1.564,4.025,0.002,5.588L12,18.586l-6.793-6.793C3.645,10.23,3.646,7.776,5.205,6.209 c0.76-0.756,1.754-1.172,2.799-1.172s2.035,0.416,2.789,1.17l0.5,0.5c0.391,0.391,1.023,0.391,1.414,0l0.5-0.5 C14.719,4.698,17.281,4.702,18.791,6.205z" />
                            </svg>
                        </div>
                        <p class="pt-1 text-gray-900">£9.99</p>
                    </a>
                </div>

            </div>
        </div>
    </section>


    <footer class="py-6 text-black bg-maintheme ">
        <div class="container px-6 mx-auto space-y-6 divide-y divide-gray-400 md:space-y-12 divide-opacity-50">
            <div class="grid grid-cols-12">
                <div class="pb-6 col-span-full md:pb-0 md:col-span-6">
                    <a rel="noopener noreferrer" href="#" class="flex justify-center space-x-3 md:justify-start">
                        <div class="flex items-center justify-center w-12 h-12 rounded-full ">
                          <img src="resources/image_resources/logo.png" alt="" srcset="">
                        </div>
                        <span class="self-center text-2xl font-semibold">FIX MIA</span>
                    </a>
                </div>
                <div class="col-span-6 text-center md:text-left md:col-span-3">
                    <p class="pb-1 text-lg font-medium">Category</p>
                    <ul>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                    </ul>
                </div>
                <div class="col-span-6 text-center md:text-left md:col-span-3">
                    <p class="pb-1 text-lg font-medium">Category</p>
                    <ul>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                        <li>
                            <a rel="noopener noreferrer" href="#" class="hover:text-white">Link</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="grid justify-center pt-6 lg:justify-between">
                <div class="flex flex-col self-center text-sm text-center md:block lg:col-start-1 md:space-x-6">
                    <span>©2023 All rights reserved</span>
                    <a rel="noopener noreferrer" href="#">
                        <span>Privacy policy</span>
                    </a>
                    <a rel="noopener noreferrer" href="#">
                        <span>Terms of service</span>
                    </a>
                </div>
                <div class="flex justify-center pt-4 space-x-4 lg:pt-0 lg:col-end-13">
                    <a rel="noopener noreferrer" href="#" title="Email" class="flex items-center justify-center w-10 h-10 bg-white rounded-full dark:text-gray-900">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5">
                            <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path>
                            <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path>
                        </svg>
                    </a>
                    <a rel="noopener noreferrer" href="#" title="Twitter" class="flex items-center justify-center w-10 h-10 bg-white rounded-full dark:text-gray-900">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 50 50" fill="currentColor" class="w-5 h-5">
                            <path d="M 50.0625 10.4375 C 48.214844 11.257813 46.234375 11.808594 44.152344 12.058594 C 46.277344 10.785156 47.910156 8.769531 48.675781 6.371094 C 46.691406 7.546875 44.484375 8.402344 42.144531 8.863281 C 40.269531 6.863281 37.597656 5.617188 34.640625 5.617188 C 28.960938 5.617188 24.355469 10.21875 24.355469 15.898438 C 24.355469 16.703125 24.449219 17.488281 24.625 18.242188 C 16.078125 17.8125 8.503906 13.71875 3.429688 7.496094 C 2.542969 9.019531 2.039063 10.785156 2.039063 12.667969 C 2.039063 16.234375 3.851563 19.382813 6.613281 21.230469 C 4.925781 21.175781 3.339844 20.710938 1.953125 19.941406 C 1.953125 19.984375 1.953125 20.027344 1.953125 20.070313 C 1.953125 25.054688 5.5 29.207031 10.199219 30.15625 C 9.339844 30.390625 8.429688 30.515625 7.492188 30.515625 C 6.828125 30.515625 6.183594 30.453125 5.554688 30.328125 C 6.867188 34.410156 10.664063 37.390625 15.160156 37.472656 C 11.644531 40.230469 7.210938 41.871094 2.390625 41.871094 C 1.558594 41.871094 0.742188 41.824219 -0.0585938 41.726563 C 4.488281 44.648438 9.894531 46.347656 15.703125 46.347656 C 34.617188 46.347656 44.960938 30.679688 44.960938 17.09375 C 44.960938 16.648438 44.949219 16.199219 44.933594 15.761719 C 46.941406 14.3125 48.683594 12.5 50.0625 10.4375 Z"></path>
                        </svg>
                    </a>
                    <a rel="noopener noreferrer" href="#" title="GitHub" class="flex items-center justify-center w-10 h-10 bg-white rounded-full dark:text-gray-900">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" class="w-5 h-5">
                            <path d="M10.9,2.1c-4.6,0.5-8.3,4.2-8.8,8.7c-0.5,4.7,2.2,8.9,6.3,10.5C8.7,21.4,9,21.2,9,20.8v-1.6c0,0-0.4,0.1-0.9,0.1 c-1.4,0-2-1.2-2.1-1.9c-0.1-0.4-0.3-0.7-0.6-1C5.1,16.3,5,16.3,5,16.2C5,16,5.3,16,5.4,16c0.6,0,1.1,0.7,1.3,1c0.5,0.8,1.1,1,1.4,1 c0.4,0,0.7-0.1,0.9-0.2c0.1-0.7,0.4-1.4,1-1.8c-2.3-0.5-4-1.8-4-4c0-1.1,0.5-2.2,1.2-3C7.1,8.8,7,8.3,7,7.6C7,7.2,7,6.6,7.3,6 c0,0,1.4,0,2.8,1.3C10.6,7.1,11.3,7,12,7s1.4,0.1,2,0.3C15.3,6,16.8,6,16.8,6C17,6.6,17,7.2,17,7.6c0,0.8-0.1,1.2-0.2,1.4 c0.7,0.8,1.2,1.8,1.2,3c0,2.2-1.7,3.5-4,4c0.6,0.5,1,1.4,1,2.3v2.6c0,0.3,0.3,0.6,0.7,0.5c3.7-1.5,6.3-5.1,6.3-9.3 C22,6.1,16.9,1.4,10.9,2.1z"></path>
                        </svg>
                    </a>
                </div>
            </div>
        </div>
    </footer>



    <script type="text/javascript">
        function dropdown() {
            document.querySelector("#submenu").classList.toggle("hidden");
            document.querySelector("#arrow").classList.toggle("rotate-0");
        }
        // dropdown();


        function openSidebar() {

            if (document.querySelector(".sidebar").classList.contains("hidden")) {
                document.querySelector(".sidebar").classList.remove("hidden");
            } else {

                document.querySelector(".sidebar").classList.add("hidden");
            }
        }
    </script>


    <script src="resources/flowbite/flowbite.min.js"></script>

    <!-- Swiper JS -->
    <script src="resources/swiper/swiper_bundle.js"></script>

    <!-- Initialize Swiper -->
    <script>
        var swiper = new Swiper("#mySwiper2 ", {

            navigation: {
                nextEl: "#next-btn",
                prevEl: "#prev-btn",
            },
            spaceBetween: 24,
            breakpoints: {
                540: {
                    slidesPerView: 1,
                    spaceBetween: 20,
                },
                640: {
                    slidesPerView: 2,
                    spaceBetween: 20,
                },
                768: {
                    slidesPerView: 4,
                    spaceBetween: 40,
                },
                1024: {
                    slidesPerView: 5,
                    spaceBetween: 10,
                },
            },
        });
    </script>
</body>

</html>