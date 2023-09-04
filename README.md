# 🎲 ONBOARD
[Play Store](https://play.google.com/store/apps/details?id=com.yapp.bol.app&pcampaignid=web_share)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.7.20-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-9.1-green.svg)](https://gradle.org/)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-21-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-32-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)

### 보드게임 레이팅 서비스
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/cf95701e-f342-4266-8d31-d8cccf911890)

<br/>

## Structure
<img width="1229" alt="Project Structure" src="https://github.com/YAPP-Github/22nd-Android-Team-2-Android/assets/83493143/ee578e57-fc3a-4670-a844-c2940de5fc44">

<br/>

## Rule
1. 급한 merge가 아니면 CI 통과 후 한 명 이상의 approve를 받았을 시 merge할 것.
2. code review - priority 1 ~ 5 맞춰서 리뷰, 리뷰 받은 것 고쳤다면 commit reply로 달아주기
3. code convention - [PRND Android Style Guide](https://github.com/PRNDcompany/android-style-guide)
4. 공유되어야 하는 지식 - Notion Dev Wiki 사용

<br/>

## Tech Stack

| Archictecture | Clean Architecture, MVVM |
| --- | --- |
| CI | Github Actions |
| DI | Hilt |
| Network | Retrofit2, OkHttp3 |
| Asynchronous | Coroutine, Flow |
| AndroidX | Paging3, LiveData, DataStore, Navigation, ViewModel |
| Collaboration Tools | GitFlow, Github, Jira, Notion, Figma |
| Library | Glide, Lottie, Social Login(Naver, Kakao, Google), ktlint, Firebase Crashlytics, Firebase Analytics, OSS License, Shimmer |

<br/>

## 기능 (ver1.0.2)
### 온보딩
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/37453981-ac66-427f-a90d-04a2dc1670b9)

### 그룹 생성 & 검색 & 가입
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/32de7169-78f1-49c2-b7a7-e961d4394d84)

### 랭킹 - 홈
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/5a66b461-8e84-4da1-a798-bc53b79a6082)

### 게임 결과 기록
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/b4154870-e6ac-4369-bc82-912ce57108e0)
![image](https://github.com/YAPP-Github/onboard-aos/assets/100047095/3a6b3acc-639b-4a51-98a6-23565c38717a)

### 안내

<br/>

## Developer
|[![](https://github.com/prk4224.png?size=100)](https://github.com/prk4224) |[![](https://github.com/eunjjungg.png?size=100)](https://github.com/eunjjungg) |[![](https://github.com/ckrudals.png?size=100)](https://github.com/ckrudals) |
|:-:|:-:|:-:|
|박재홍|정은정|차경민|

<br/>

## Refactoring
1. [Data, Domain Module Package Refactoring](https://www.figma.com/file/05EWgiGjm4LCIq9y9WEgt7/package-refactoring?type=whiteboard&node-id=0%3A1&t=rhrzacia1cOJGYDM-1)
