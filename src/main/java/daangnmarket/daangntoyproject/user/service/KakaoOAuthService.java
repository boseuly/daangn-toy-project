package daangnmarket.daangntoyproject.user.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
public class KakaoOAuthService {
    public String getToken(String code) throws IOException {
        // 인가코드로 토큰받기
        /*
            구현 과정
        1. connection 생성
        2. POST로 보낼 Body 작성
        3. 받아온 결과 JSON 파싱 (Gson)
         */

        // 1. connection 생성
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로 해준다.
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);        // 데이터 기록 알려주기

            // 2. post로 보낼 바디 작성(POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송)
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=3b9ab37490257b10f89f741a7912ceb7");       // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://43.201.5.60:8085/oauth/kakao");   // TODO 인가코드 받은 redirect_uri 입력 배포될 uri로 작성
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            // 결과 code가 200이면 성공
            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // 3. 받아온 결과 JSON으로 파싱
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // Json 라이브러리에 포함된 클래스로 json parsing 객체 생성
            JSONObject elem = (JSONObject) new JSONParser().parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();

            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return token;
    }


    // 사용자 정보 가져오기
    public Map<String, Object> getUserInfo(String access_token) throws IOException {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> result = new HashMap<>();

        // Access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            // 받아온 결과가 200이면 성공
            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);


            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";

            while((line=br.readLine())!=null) {
                res+=line;
            }

            System.out.println("res = " + res);

            // JSON 라이브러리로 JSON 파싱
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            JSONObject properties = (JSONObject) obj.get("properties");


            String id = obj.get("id").toString();
            String email = kakao_account.get("email").toString();
            String nickname = properties.get("nickname").toString();
            String profile_image = properties.get("profile_image").toString();


            result.put("id", id);
            result.put("email", email);
            result.put("nickname", nickname);
            result.put("profile_image", profile_image);

            br.close();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getAgreementInfo(String access_token)
    {
        String result = "";
        String host = "https://kapi.kakao.com/v2/user/scopes";
        try{
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer "+access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while((line=br.readLine())!=null)
            {
                result+=line;
            }

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // result is json format
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

//    // 로그아웃 기능 -> spring security 에서
//    public void kakaoLogout(String access_Token) {
//        String reqURL = "https://kakao.api.com/user/logout";
//        try {
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String result = "";
//            String line = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("Logout 결과 : " + result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
