
  const char features[] = {"\n"
"CXX_FEATURE:"
#if (__GNUC__ * 100 + __GNUC_MINOR__) >= 500 && __cplusplus >= 201402L
"1"
#else
"0"
#endif
"cxx_aggregate_default_initializers\n"
"CXX_FEATURE:"
#if (__GNUC__ * 100 + __GNUC_MINOR__) >= 407 && __cplusplus >= 201103L
"1"
#else
"0"
#endif
"cxx_alias_templates\n"
"CXX_FEATURE:"
#if (__GNUC__ * 100 + __GNUC_MINOR__) >= 408 && __cplusplus >= 201103L
"1"
#else
"0"
#endif
"cxx_alignas\n"
