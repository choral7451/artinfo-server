import {Injectable} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import * as bcrypt from 'bcrypt';
import {UserService} from "@/apis/user/user.service";
import { JwtService } from '@nestjs/jwt';
import {User} from "@prisma/client";

@Injectable()
export class AuthService {
  constructor(
    private readonly prismaService: PrismaService,
    private readonly jwtService: JwtService,
    private readonly userService: UserService,
  ) {}

  async login(email: string, password: string) {
    const user = await this.userService.getUserByEmail(email)
    const passwordMatching = await bcrypt.compare(password, user.password);
    if (!passwordMatching) throw new Error('PASS_WORD_DOES_NOT_MATCH');

    return this.getTokensByUserId(user.id)
  }

  async getTokensByUserId(userId: string) {
    const user = await this.userService.getUserById(userId)
    return { accessToken: this.getAccessToken(user), refreshToken: this.getRefreshToken() }
  }

  async restoreTokens(userId: string, refreshToken: string) {
    const result = this.jwtService.verify(refreshToken, { secret: process.env.JWT_TOKEN_KEY });
    if(!result) throw new Error('THE_TOKEN_IS_INVALID')

    return this.getTokensByUserId(userId)
  }

  private getAccessToken(user: User): string {
    return this.jwtService.sign(
      { id: user.id, nickname: user.nickname, email: user.email }, //
      { privateKey: process.env.JWT_TOKEN_KEY, expiresIn: '1h' },
    );
  }

  private getRefreshToken(): string {
    return this.jwtService.sign({}, { privateKey: process.env.JWT_TOKEN_KEY, expiresIn: '1w' });
  }
}